package rs.ac.fink.izbori.core;

import java.io.*;

public class ReplicatedLog {

    public interface LogReplicator {
        void replicateOnFollowers(Long entryAtIndex, byte[] data);
    }
    
    
    Long lastLogEntryIndex=0L;
    Long lastSnapshotIndex=0L;
    final LogReplicator leaderReplicaNode;
    FileOutputStream fs;
    OutputStreamWriter writer;
    String logFileName;
    String snapshotFileName;
    
    
    public ReplicatedLog(String fileName,LogReplicator node) throws FileNotFoundException{
        this.leaderReplicaNode=node;
        this.logFileName=fileName;
        fs=new FileOutputStream(fileName, true);
        writer=new OutputStreamWriter(fs);
    }
        
    public synchronized void appendAndReplicate(byte[] commandBytes) throws IOException{
        Long lastLogEntryIndex=appendToLocalLog(commandBytes);
        leaderReplicaNode.replicateOnFollowers(lastLogEntryIndex, commandBytes);
        
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

    public Long getLastLogEntryIndex(){
        return lastLogEntryIndex;
    }
    
    public void close() throws IOException {
        writer.close();
        fs.close();
    }
}