package rs.ac.fink.izbori.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.zookeeper.KeeperException;
import rs.ac.fink.izbori.commands.SubmitVotesCommand;
import rs.ac.fink.izbori.core.*;
import rs.ac.fink.izbori.model.*;
import rs.ac.fink.izbori.service.*;

import java.io.*;
import java.net.InetAddress;
import java.util.*;

public class ElectionAppServer implements ReplicaNode.LogCommandExecutor {
    
    public static final String APP_ROOT_NODE="/election";
    
    public enum VoteExecutionStatus { 
        OK, VERIFIED, INVALID_SUM, NOT_LEADER, BLOCKED, RETRY_NEEDED 
    }
    
    public record VoteOperationResult(VoteExecutionStatus status,String message, boolean verified){}
    
    ReplicaNode myReplicaNode = null;
    ElectionServiceImpl electionService;
    ElectionState state;
    
    public ElectionAppServer(ElectionState initialState,String zkAddress, String zkRoot,String myGRPCAddress,String logFileName) throws Exception{
        this.state = initialState;
        this.electionService= new ElectionServiceImpl(state);
        this.myReplicaNode =new ReplicaNode(zkAddress, zkRoot, myGRPCAddress, logFileName, this);
        
    }
    
    protected ReplicaNode getReplicaNode(){
        return myReplicaNode;
    }
    
    @Override
    public void executeReplicatedLogCommand(byte[] commandBytes){
        String commandStr= new String(commandBytes);
        String[] tokens =commandStr.split(" ");
        
        if (SubmitVotesCommand.SUBMIT_VOTES.equals(tokens[0])){
            SubmitVotesCommand command = SubmitVotesCommand.deserialize(tokens);
            submitVotesInternal(command.getSubmission(), false);
        }
    }
    
    
    public VoteOperationResult submitVotes(VoteSubmission submission){
        return submitVotesInternal(submission, true);
    }
    
    private VoteOperationResult submitVotesInternal(VoteSubmission submission, boolean asLeaderToExecute){
        if (asLeaderToExecute && !myReplicaNode.isLeader()){
            return new VoteOperationResult(VoteExecutionStatus.NOT_LEADER, "Not leader", false);
        }
        
        if (asLeaderToExecute){
            SubmitVotesCommand command = new SubmitVotesCommand(submission);
            try {
                myReplicaNode.getReplicatedLog().appendAndReplicate(command.serialize().getBytes());
            } catch (IOException e){
                e.printStackTrace();
                return new VoteOperationResult(VoteExecutionStatus.OK, "Log error", false);
            }
        }
        
        ElectionServiceImpl.SubmissionResult result = electionService.submitVotes(submission);
        
        VoteExecutionStatus status = switch (result.status){
            case OK->VoteExecutionStatus.OK;
            case VERIFIED->VoteExecutionStatus.VERIFIED;
            case INVALID_SUM->VoteExecutionStatus.INVALID_SUM;
            case BLOCKED->VoteExecutionStatus.BLOCKED;
            case RETRY_NEEDED->VoteExecutionStatus.RETRY_NEEDED;
            default->VoteExecutionStatus.OK;
        };
        
        return new VoteOperationResult(status, result.message,result.verified);
    }
    
    public Statistics getStatistics() {
        if (!myReplicaNode.isLeader() && !myReplicaNode.isSynchronized()) {
            throw new RuntimeException("Follower not synchronized");
        }
        
        return electionService.getStatistics();
    }
    
    public static void main(String[] args) throws Exception {
        if (args.length != 4){
            System.out.println("Usage: ElectionAppServer <zookeeper_host:port> <gRPC_port> <log_file> <config.json>");
            System.exit(1);
        }
        
        String zkConnectionString =args[0];
        int gRPCPort =Integer.parseInt(args[1]);
        String logFileName=args[2];
        String configFile=args[3];
        
        String myGRPCaddress=InetAddress.getLocalHost().getHostName() + ":" + gRPCPort;
        
        ObjectMapper mapper =new ObjectMapper();
        ElectionConfig config= mapper.readValue(new File(configFile), ElectionConfig.class);
        
        ElectionState initialState=new ElectionState(config.getElectionType(), config.getCandidates());
        Random random=new Random();
        for (int i = 0; i < config.getPollingStationsCount(); i++){
            int registered = config.getRegisteredVotersMin()+random.nextInt(config.getRegisteredVotersMax()-config.getRegisteredVotersMin());
            initialState.addPollingStation(new PollingStation(i, registered));
        }
        
        ElectionAppServer appServer=new ElectionAppServer(initialState, zkConnectionString, APP_ROOT_NODE, myGRPCaddress, logFileName);
        
        Server gRPCServer=ServerBuilder
            .forPort(gRPCPort)
            .addService(new ElectionServiceGRPCServer(appServer))
            .addService(appServer.getReplicaNode())
            .build();

        gRPCServer.start();
        
        try{
            appServer.getReplicaNode().leaderElection();
            appServer.getReplicaNode().start();
            
            gRPCServer.awaitTermination();
            
            appServer.getReplicaNode().stop();
            
        }catch (KeeperException | InterruptedException e){
            e.printStackTrace();
        }
    }
}