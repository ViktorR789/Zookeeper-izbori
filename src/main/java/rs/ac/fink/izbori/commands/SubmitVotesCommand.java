package rs.ac.fink.izbori.commands;

import rs.ac.fink.izbori.model.VoteSubmission;
import java.util.Map;

public class SubmitVotesCommand implements Command {
    private VoteSubmission submission;
    
    public SubmitVotesCommand(VoteSubmission submission){
        this.submission=submission;
    }
    
    public VoteSubmission getSubmission(){
        return submission;
    }
    
    @Override
    public String serialize(){
        StringBuilder sb=new StringBuilder();
        sb.append(SUBMIT_VOTES).append(" ");
        sb.append(submission.getControllerId()).append(" ");
        sb.append(submission.getStationId()).append(" ");
        sb.append(submission.getTotalVoted()).append(" ");
        sb.append(submission.getInvalidVotes());
        
        for (Map.Entry<String,Integer> entry : submission.getVotesPerCandidate().entrySet()){
            sb.append(" ").append(entry.getKey()).append(":").append(entry.getValue());
        }
        
        return sb.toString();
    }
    
    public static SubmitVotesCommand deserialize(String[] tokens){
        int controllerId=Integer.parseInt(tokens[1]);
        int stationId=Integer.parseInt(tokens[2]);
        int totalVoted=Integer.parseInt(tokens[3]);
        int invalidVotes=Integer.parseInt(tokens[4]);
        
        Map<String,Integer> votes=new java.util.HashMap<>();
        for (int i=5; i < tokens.length; i++){
            String[] parts=tokens[i].split(":");
            votes.put(parts[0],Integer.parseInt(parts[1]));
        }
        
        VoteSubmission submission=new VoteSubmission(controllerId,stationId,totalVoted,invalidVotes,votes);
        return new SubmitVotesCommand(submission);
    }
}