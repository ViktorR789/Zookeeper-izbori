package rs.ac.fink.izbori.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ElectionConfig {
    @JsonProperty("election_type")
    private String electionType;
    
    @JsonProperty("polling_stations_count")
    private int pollingStationsCount;
    
    @JsonProperty("candidates")
    private List<String> candidates;
    
    @JsonProperty("registered_voters_per_station_min")
    private int registeredVotersMin;
    
    @JsonProperty("registered_voters_per_station_max")
    private int registeredVotersMax;

    public String getElectionType() { return electionType; }
    public void setElectionType(String electionType) { this.electionType = electionType; }
    
    public int getPollingStationsCount() { return pollingStationsCount; }
    public void setPollingStationsCount(int count) { this.pollingStationsCount = count; }
    
    public List<String> getCandidates() { return candidates; }
    public void setCandidates(List<String> candidates) { this.candidates = candidates; }
    
    public int getRegisteredVotersMin() { return registeredVotersMin; }
    public void setRegisteredVotersMin(int min) { this.registeredVotersMin = min; }
    
    public int getRegisteredVotersMax() { return registeredVotersMax; }
    public void setRegisteredVotersMax(int max) { this.registeredVotersMax = max; }
}