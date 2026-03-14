package rs.ac.fink.izbori.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PollingStation implements Serializable {
    private int stationId;
    private int registeredVoters;
    private Integer totalVoted=null;
    private Integer invalidVotes=null;
    private Map<String, Integer> votesPerCandidate=new HashMap<>();
    
    private Map<Integer, VoteSubmission> controllerSubmissions=new HashMap<>();
    private boolean verified=false;
    private int verificationAttempts=0;
    private boolean blocked=false;

    public PollingStation(int stationId, int registeredVoters){
        this.stationId=stationId;
        this.registeredVoters=registeredVoters;
    }

    public int getStationId(){ return stationId; }
    public int getRegisteredVoters(){ return registeredVoters; }
    
    public Integer getTotalVoted(){ return totalVoted; }
    public void setTotalVoted(Integer totalVoted){ this.totalVoted=totalVoted; }
    
    public Integer getInvalidVotes(){ return invalidVotes; }
    public void setInvalidVotes(Integer invalidVotes){ this.invalidVotes=invalidVotes; }
    
    public Map<String, Integer> getVotesPerCandidate(){ return votesPerCandidate; }
    
    public Map<Integer, VoteSubmission> getControllerSubmissions(){ return controllerSubmissions; }
    
    public boolean isVerified(){ return verified; }
    public void setVerified(boolean verified){ this.verified=verified; }
    
    public int getVerificationAttempts(){ return verificationAttempts; }
    public void incrementVerificationAttempts(){ this.verificationAttempts++; }
    
    public boolean isBlocked(){ return blocked; }
    public void setBlocked(boolean blocked){ this.blocked=blocked; }
}