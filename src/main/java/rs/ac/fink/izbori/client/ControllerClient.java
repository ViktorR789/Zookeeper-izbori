package rs.ac.fink.izbori.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;

import com.fasterxml.jackson.databind.ObjectMapper;

import rs.ac.fink.izbori.core.*;
import rs.ac.fink.izbori.grpc.*;
import rs.ac.fink.izbori.model.ElectionConfig;
import rs.ac.fink.izbori.model.VoteData;
import rs.ac.fink.izbori.server.ElectionAppServer;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class ControllerClient extends SyncPrimitive {
    
    final String appRoot;
    String leaderNodeName=null;
    String leaderHostNamePort;
    Object zkNotifier =new Object();  
    
    ManagedChannel channel=null;
    ElectionServiceGrpc.ElectionServiceBlockingStub blockingStub=null;
    
    protected ControllerClient(String zkAddress,String appRoot) throws KeeperException, InterruptedException {
        super(zkAddress);
        this.appRoot=appRoot;
    }
    
    public enum VoteExecutionStatus { 
        OK, VERIFIED, INVALID_SUM, NOT_LEADER, BLOCKED, RETRY_NEEDED 
    }
    
    @Override
    public void process(WatchedEvent event){
        System.out.println(">>> Stigla notifikacija od ZooKeepera: "+event.getType());
        try {
            synchronized(zkNotifier){
                zkNotifier.notifyAll();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    protected void newLeaderAwaiting() throws KeeperException, InterruptedException{
        System.out.println(">>> Trazenje novog lidera!");
        
        synchronized(zkNotifier){
            zkNotifier.wait();
        }
        checkLeader();
    }
    
    public synchronized void checkLeader() throws KeeperException, InterruptedException{
        List<String> list=zk.getChildren(appRoot, false);  
        System.out.println("There are total: "+list.size() + " replicas for elections!");
        for (int i=0; i < list.size(); i++) 
            System.out.print("NODE:"+list.get(i) + ", ");
        System.out.println();
        
        if (list.size() == 0){
            System.out.println("0 Elemenata ?");
        } else {
            
            Integer minValue=Integer.parseInt(list.get(0).substring(ReplicaNode.REPLICA_NODE_SEQUENCE_INDEX));
            String minNodeName=list.get(0);
            
            for(int i=1; i < list.size(); i++){
                Integer tempValue=Integer.parseInt(list.get(i).substring(ReplicaNode.REPLICA_NODE_SEQUENCE_INDEX));
                if(minValue > tempValue){
                    minValue=tempValue;
                    minNodeName=list.get(i);
                }
            }
            
            if (leaderNodeName == null || !minNodeName.equals(leaderNodeName)){
                leaderNodeName=minNodeName;
                byte[] b=zk.getData(appRoot + "/" + leaderNodeName, true, null);  
                leaderHostNamePort=new String(b);
                
                System.out.println(">>> Leader je " + leaderNodeName + " at " + leaderHostNamePort);
                
                //if (channel != null)
                //    channel.shutdown();
                
                blockingStub=getBlockingStub(leaderHostNamePort);
            }
        }
    }
    
    public ElectionServiceGrpc.ElectionServiceBlockingStub getBlockingStub(String hostNamePort){
        String[] splits=hostNamePort.split(":");
        channel=ManagedChannelBuilder.forAddress(splits[0], Integer.parseInt(splits[1]))
            .usePlaintext()
            .build();

        return ElectionServiceGrpc.newBlockingStub(channel);
    }
    
    private ElectionServiceGrpc.ElectionServiceBlockingStub getStub(){
        synchronized (this){  
            return blockingStub;
        }
    }
    
    public void simulateControllers(int numControllers, int numStations,List<String> candidates) throws InterruptedException {
        ExecutorService executor=Executors.newFixedThreadPool(numControllers);
        Random random =new Random();
        Set<Integer> concurrentSet = ConcurrentHashMap.newKeySet();
        
        Map<Integer, VoteData> truthData = new HashMap<>();
        for (int stationId=0;stationId<numStations; stationId++) {
            int totalVoted=100+random.nextInt(200);
            int invalidVotes = random.nextInt(10);
            int validVotes = totalVoted - invalidVotes;
            
            Map<String,Integer> votes=new HashMap<>();
            int remaining=validVotes;
            for (int i=0;i<candidates.size()-1; i++) {
                int vote=random.nextInt(remaining + 1);
                votes.put(candidates.get(i),vote);
                remaining -= vote;
            }
            votes.put(candidates.get(candidates.size()-1),remaining);
            
            truthData.put(stationId, new VoteData(totalVoted,invalidVotes,votes));
        }
        
        for (int controllerId=0; controllerId <numControllers;controllerId++){
            final int cId=controllerId;
            executor.submit(()->{
                for (int stationId=0; stationId < numStations; stationId++){
                    try {
                    	VoteData truth=truthData.get(stationId);
                        boolean makeError=random.nextDouble()<0.5;
                        
                        int totalVoted=truth.totalVoted;
                        int invalidVotes=truth.invalidVotes;
                        Map<String,Integer>votes=new HashMap<>(truth.votes);
                        
                        if (makeError){
                        	invalidVotes+=random.nextInt(3)-1;
                            if (invalidVotes< 0) invalidVotes=0;
                            if (invalidVotes >=totalVoted) invalidVotes=Math.max(0,totalVoted-1);  
                        }
                        
                        VoteRequest request=VoteRequest.newBuilder()
                            .setControllerId(cId)
                            .setStationId(stationId)
                            .setTotalVoted(totalVoted)
                            .setInvalidVotes(invalidVotes)
                            .putAllVotesPerCandidate(votes)
                            .build();
                        
                        int maxRetries=5;
                        int retryCount=0;
                        VoteResponse response=null;
                        
                        while (retryCount< maxRetries){
                            try {
                                response =getStub().submitVotes(request);
                                break;
                            } catch (io.grpc.StatusRuntimeException e){
                                if (e.getStatus().getCode()==io.grpc.Status.Code.UNAVAILABLE){
                                    System.err.println("Controller "+cId + " - ERROR - Server has crashed!");
                                    try {
                                        newLeaderAwaiting();  
                                    } catch (Exception ex){
                                        ex.printStackTrace();
                                    }
                                    retryCount++;
                                } else {
                                    throw e;
                                }
                            }
                        }
                        
                                                
                        if(response != null){
                        	String status = response.getStatus().toString();
                            String message = response.getMessage();
                            System.out.println("Controller " + cId + " Station " +stationId + 
                                ": " + response.getStatus() + " - "+ response.getMessage());
                            if(status.equals("NEEDS_RETRY")){
                            	concurrentSet.add(stationId);
                            	System.out.println("ELECTION WILL BE REPEATED ON STATION "+stationId);
                            }
                        }else {
                            System.err.println("Controller " +cId +" Station " + stationId + 
                                ": FAILED after "+maxRetries +" retries");
                        }
                        
                        Thread.sleep(100+random.nextInt(200));
                        
                    } catch (Exception e){
                        System.err.println("Controller " + cId + " error: " + e.getMessage());
                    }
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(5,TimeUnit.MINUTES);
        
        if (!concurrentSet.isEmpty()) {
            System.out.println("\n>>> SECOND ROUND " + concurrentSet.size() + " stations\n");
            
            executor=Executors.newFixedThreadPool(numControllers);
            
            for (int controllerId=0; controllerId<numControllers;controllerId++) {
                final int cId=controllerId;
                executor.submit(()->{
                    for (Integer stationId : concurrentSet) {
                        try {
                            VoteData truth=truthData.get(stationId);
                            boolean makeError=random.nextDouble() < 0.5;
                            
                            int totalVoted=truth.totalVoted;
                            int invalidVotes=truth.invalidVotes;
                            Map<String, Integer> votes=new HashMap<>(truth.votes);
                            
                            if (makeError) {
                                invalidVotes+=random.nextInt(3)-1;
                                if (invalidVotes<0) invalidVotes=0;
                                if (invalidVotes>=totalVoted) invalidVotes =Math.max(0, totalVoted - 1);  
                            }
                            
                            VoteRequest request=VoteRequest.newBuilder()
                                .setControllerId(cId)
                                .setStationId(stationId)
                                .setTotalVoted(totalVoted)
                                .setInvalidVotes(invalidVotes)
                                .putAllVotesPerCandidate(votes)
                                .build();
                            
                            int maxRetries=5;
                            int retryCount=0;
                            VoteResponse response =null;
                            
                            while (retryCount<maxRetries) {
                                try {
                                    response=getStub().submitVotes(request);
                                    break;
                                } catch (io.grpc.StatusRuntimeException e) {
                                    if (e.getStatus().getCode() == io.grpc.Status.Code.UNAVAILABLE) {
                                        System.err.println("Controller " + cId +" - ERROR - Server crashed!");
                                        try {
                                            newLeaderAwaiting();
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                        retryCount++;
                                    } else {
                                        throw e;
                                    }
                                }
                            }
                            
                            if (response!=null) {
                                String status=response.getStatus().toString();
                                
                                System.out.println("Controller "+cId +" Station "+stationId+ 
                                    " (RETRY): "+status+" - " + response.getMessage());
                                
                                if (status.equals("VERIFIED")) {
                                    concurrentSet.remove(stationId);
                                    System.out.println("   >>> Stanica "+stationId+" uspešno verifikovana!");
                                }
                                else if (status.equals("BLOCKED")) {
                                    concurrentSet.remove(stationId);
                                    System.err.println("   >>> Stanica "+stationId+" je BLOKIRANA!");
                                }
                            } else {
                                System.err.println("Controller "+cId+" Station " + stationId + 
                                    " (RETRY): FAILED after "+maxRetries+" retries");
                            }
                            
                            Thread.sleep(100+random.nextInt(200));
                            
                        } catch (Exception e) {
                            System.err.println("Controller "+cId+ " error: "+e.getMessage());
                        }
                    }
                });
            }
            
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.MINUTES);
            
            try {
                StatisticsRequest statsReq= StatisticsRequest.newBuilder().build();
                StatisticsResponse stats=getStub().getStatistics(statsReq);
                
                System.out.println("\n=== FINAL STATISTICS ===");
                System.out.println("Percentage reported: " + stats.getPercentageReported() + "%");
                System.out.println("Turnout: " + stats.getTurnoutPercentage() + "%");
                System.out.println("Results:");
                for (Map.Entry<String,Integer> entry : stats.getResultsPerCandidateMap().entrySet()) {
                    System.out.println("  " +entry.getKey() +": " + entry.getValue()+ " votes");
                }
                System.out.println("Stations needing retry: "+stats.getStationsNeedingRetry());
                System.out.println("Verified stations: "+stats.getVerifiedStations()+"/"+ stats.getTotalStations());
            } catch (Exception e) {
                System.err.println("Failed to get final statistics: " + e.getMessage());
            }
            
            if (channel != null) {
                channel.shutdown();
            }
        }
    }
    
    public static void main(String[] args){
        if (args.length !=2){
            System.out.println("Usage: ControllerClient <zookeeper_host:port> <config file>");
            System.exit(1);
        }
        
        try{
            ControllerClient client =new ControllerClient(args[0], ElectionAppServer.APP_ROOT_NODE);
            client.checkLeader(); 
            String configFile=args[1];
            ObjectMapper mapper =new ObjectMapper();
            ElectionConfig config= mapper.readValue(new File(configFile), ElectionConfig.class);
            
            int numControllers= 5;
            int numStations =config.getPollingStationsCount();
            List<String> candidates= config.getCandidates();
            
            client.simulateControllers(numControllers,numStations,candidates);
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}