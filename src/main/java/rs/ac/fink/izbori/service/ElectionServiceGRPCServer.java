package rs.ac.fink.izbori.service;

import io.grpc.stub.StreamObserver;
import rs.ac.fink.izbori.grpc.*;
import rs.ac.fink.izbori.model.VoteSubmission;
import rs.ac.fink.izbori.server.ElectionAppServer;

import java.util.HashMap;
import java.util.Map;

public class ElectionServiceGRPCServer extends ElectionServiceGrpc.ElectionServiceImplBase {
    
    private final ElectionAppServer appServer;
    
    public ElectionServiceGRPCServer(ElectionAppServer appServer){
        this.appServer=appServer;
    }
    
    @Override
    public void submitVotes(VoteRequest request,StreamObserver<VoteResponse> responseObserver){
        Map<String, Integer> votes =new HashMap<>(request.getVotesPerCandidateMap());
        VoteSubmission submission= new VoteSubmission(
            request.getControllerId(),
            request.getStationId(),
            request.getTotalVoted(),
            request.getInvalidVotes(),
            votes
        );
        
        ElectionAppServer.VoteOperationResult result= appServer.submitVotes(submission);
        
        VoteStatus status =convertStatus(result.status());
        VoteResponse response = VoteResponse.newBuilder()
            .setStatus(status)
            .setMessage(result.message())
            .setVerified(result.verified())
            .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
    private VoteStatus convertStatus(ElectionAppServer.VoteExecutionStatus status){
        return switch (status){
            case OK -> VoteStatus.OK;
            case VERIFIED -> VoteStatus.STATION_VERIFIED;
            case INVALID_SUM -> VoteStatus.INVALID_SUM;
            case NOT_LEADER -> VoteStatus.NOT_LEADER;
            case BLOCKED -> VoteStatus.STATION_BLOCKED;
            case RETRY_NEEDED -> VoteStatus.NEEDS_RETRY;
            default -> VoteStatus.OK;
        };
    }
}