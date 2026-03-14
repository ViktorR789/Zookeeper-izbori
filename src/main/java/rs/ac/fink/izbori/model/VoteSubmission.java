package rs.ac.fink.izbori.model;

import java.io.Serializable;
import java.util.Map;

public class VoteSubmission implements Serializable{
    private int controllerId;
    private int stationId;
    private int totalVoted;
    private int invalidVotes;
    private Map<String,Integer> votesPerCandidate;

    public VoteSubmission(int controllerId,int stationId,int totalVoted,
                          int invalidVotes,Map<String,Integer> votesPerCandidate) {
        this.controllerId =controllerId;
        this.stationId =stationId;
        this.totalVoted=totalVoted;
        this.invalidVotes=invalidVotes;
        this.votesPerCandidate=votesPerCandidate;
    }

    public int getControllerId() { return controllerId; }
    public int getStationId() { return stationId; }
    public int getTotalVoted() { return totalVoted; }
    public int getInvalidVotes() { return invalidVotes; }
    public Map<String,Integer> getVotesPerCandidate() { return votesPerCandidate; }
    
    public int getValidVotes() {
        return totalVoted - invalidVotes;
    }
    
    public int getTotalCandidateVotes() {
        return votesPerCandidate.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    public boolean isValid(){
        return getValidVotes() == getTotalCandidateVotes();
    }
    
    @Override
    public boolean equals(Object o){
       if(this == o) return true;
       if(!(o instanceof VoteSubmission)) return false;
        VoteSubmission that=(VoteSubmission) o;
        return totalVoted ==that.totalVoted &&
               invalidVotes == that.invalidVotes &&
               votesPerCandidate.equals(that.votesPerCandidate);
    }
    
    
}