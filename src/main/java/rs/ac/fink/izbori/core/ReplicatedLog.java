package rs.ac.fink.izbori.core;

import java.io.*;

import com.google.protobuf.ByteString;

import rs.ac.fink.izbori.core.ReplicaNode.LogCommandExecutor;
import rs.ac.fink.izbori.grpc.LogEntry;
import rs.ac.fink.izbori.model.ElectionState;

public class ReplicatedLog {

    public interface LogReplicator {
        void replicateOnFollowers(Long entryAtIndex, byte[] data);
    }
    
    //==============================================
    public interface StateProvider {
        void saveSnapshot(String filename) throws IOException;
        Object loadSnapshot(String filename) throws IOException, ClassNotFoundException;
        void applyCommand(byte[] commandBytes);
    }
    private static final int SNAPSHOT_INTERVAL = 100;
    
    
    Long lastLogEntryIndex=0L;
    Long lastSnapshotIndex=0L;
    final LogReplicator leaderReplicaNode;
    final StateProvider stateProvider;
    FileOutputStream fs;
    OutputStreamWriter writer;
    String logFileName;
    String snapshotFileName;
    
    //************************************************************
    public ReplicatedLog(String fileName, LogReplicator node, StateProvider stateProvider) throws FileNotFoundException {
        this.leaderReplicaNode = node;
        this.stateProvider = stateProvider;
        this.logFileName = fileName;
        this.snapshotFileName = fileName + ".snapshot";
        fs = new FileOutputStream(fileName, true);
        writer = new OutputStreamWriter(fs);
    }
        
    public synchronized void appendAndReplicate(byte[] commandBytes) throws IOException{
        Long lastLogEntryIndex=appendToLocalLog(commandBytes);
        leaderReplicaNode.replicateOnFollowers(lastLogEntryIndex, commandBytes);
        if (lastLogEntryIndex - lastSnapshotIndex >= SNAPSHOT_INTERVAL) {
            takeSnapshot();
        }
    }
    
    public void replayLog() throws IOException, ClassNotFoundException {
        File snapshotFile =new File(snapshotFileName);
        if (snapshotFile.exists()) {
            stateProvider.loadSnapshot(snapshotFileName);
            try (BufferedReader br=new BufferedReader(new FileReader(snapshotFileName + ".meta"))) {
                lastSnapshotIndex=Long.parseLong(br.readLine());
                lastLogEntryIndex=lastSnapshotIndex;
            }
            System.out.println("Loaded snapshot at index: " + lastSnapshotIndex);
        }
        
        File logFile = new File(logFileName);
        if (logFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(logFileName))) {
                String line;
                long index = 0;
                while ((line=br.readLine()) != null) {
                    index++;
                    if (index<= lastSnapshotIndex) continue;
                    
                    stateProvider.applyCommand(line.getBytes());
                    lastLogEntryIndex = index;
                }
            }
        }
    }
    
    public Long appendToLocalLog(byte[] data) throws IOException{
        String s=new String(data);
        System.out.println("Log #"+lastLogEntryIndex+": "+s);
        
        writer.write(s);
        writer.write("\r\n");
        writer.flush();
        fs.flush();
        
        return ++lastLogEntryIndex;
    }
    
    
    
    private void takeSnapshot() throws IOException {
        stateProvider.saveSnapshot(snapshotFileName);
        lastSnapshotIndex=lastLogEntryIndex;
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(snapshotFileName + ".meta"))) {
            bw.write(String.valueOf(lastSnapshotIndex));
        }
        
        writer.close();
        fs.close();
        fs = new FileOutputStream(logFileName, true);
        writer = new OutputStreamWriter(fs);
    }
    
    public void syncFollower(FollowerGRPCChannel channel, long followerLastIndex, long leaderIndex) {
        for (long i = followerLastIndex + 1; i <= leaderIndex; i++) {
            byte[] logEntry;
			try {
				logEntry=readLogEntryAtIndex(i);
				LogEntry entry = LogEntry.newBuilder()
		                .setEntryAtIndex(i)
		                .setLogEntryData(ByteString.copyFrom(logEntry))
		                .build();
		            channel.getBlockingStub().appendLog(entry);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
            
        }
    }
    
    public byte[] readLogEntryAtIndex(long index) throws IOException {
        try (BufferedReader reader=new BufferedReader(new FileReader(logFileName))) {
            String line;
            long currentIndex=1;
            while ((line = reader.readLine()) != null) {
                if (currentIndex==index) {
                    return line.getBytes();
                }
                currentIndex++;
            }
        }
        return null;
    }

    public Long getLastLogEntryIndex(){
        return lastLogEntryIndex;
    }
    
    public void close() throws IOException {
        writer.close();
        fs.close();
    }
}