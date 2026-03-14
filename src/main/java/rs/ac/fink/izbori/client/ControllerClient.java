package rs.ac.fink.izbori.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import rs.ac.fink.izbori.core.*;
import rs.ac.fink.izbori.grpc.*;
import rs.ac.fink.izbori.server.ElectionAppServer;

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
    
    @Override
    public void process(WatchedEvent event){
        System.out.println(">>> Stigla notifikacija od ZooKeepera: "+event.getType());
        try {
            synchronized(zkNotifier){
                zkNotifier.notify();
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
    
    public void simulateControllers(int numControllers, int numStations) throws InterruptedException {
        ExecutorService executor=Executors.newFixedThreadPool(numControllers);
        Random random =new Random();
        List<String> candidates= Arrays.asList("Candidate_A", "Candidate_B", "Candidate_C");
        
        for (int controllerId=0; controllerId <numControllers;controllerId++){
            final int cId=controllerId;
            executor.submit(()->{
                for (int stationId=0; stationId < numStations; stationId++){
                    try {
                        int totalVoted=100 + random.nextInt(200);
                        int invalidVotes=random.nextInt(10);
                        int validVotes=totalVoted - invalidVotes;
                        
                        Map<String, Integer> votes=new HashMap<>();
                        int remaining=validVotes;
                        for(int i =0; i <candidates.size()-1;i++){
                            int vote= random.nextInt(remaining+1);
                            votes.put(candidates.get(i), vote);
                            remaining -= vote;
                        }
                        votes.put(candidates.get(candidates.size() - 1), remaining);
                        
                        VoteRequest request=VoteRequest.newBuilder()
                            .setControllerId(cId)
                            .setStationId(stationId)
                            .setTotalVoted(totalVoted)
                            .setInvalidVotes(invalidVotes)
                            .putAllVotesPerCandidate(votes)
                            .build();
                        
                        int maxRetries =5;
                        int retryCount =0;
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
                            System.out.println("Controller " + cId + " Station " +stationId + 
                                ": " + response.getStatus() + " - "+ response.getMessage());
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
        executor.awaitTermination(5, TimeUnit.MINUTES);
        
    }
    
    public static void main(String[] args){
        if (args.length !=3){
            System.out.println("Usage: ControllerClient <zookeeper_host:port> <num_controllers> <num_stations>");
            System.exit(1);
        }
        
        try{
            ControllerClient client =new ControllerClient(args[0], ElectionAppServer.APP_ROOT_NODE);
            client.checkLeader(); 
            
            int numControllers= Integer.parseInt(args[1]);
            int numStations =Integer.parseInt(args[2]);
            
            client.simulateControllers(numControllers,numStations);
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}