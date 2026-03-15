package rs.ac.fink.izbori.model;

import java.util.Map;

public class VoteData {
	public int totalVoted;
    public int invalidVotes;
    public Map<String, Integer> votes;
    
    public VoteData(int totalVoted, int invalidVotes, Map<String, Integer> votes) {
        this.totalVoted = totalVoted;
        this.invalidVotes = invalidVotes;
        this.votes = votes;
    }
}
