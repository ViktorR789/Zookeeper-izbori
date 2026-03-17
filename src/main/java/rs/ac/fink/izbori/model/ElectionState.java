package rs.ac.fink.izbori.model;

import java.io.*;
import java.util.*;

public class ElectionState implements Serializable{
    private static final long serialVersionUID=1L;
    
    private String electionType;
    private List<String> candidates;
    private Map<Integer, PollingStation> pollingStations=new HashMap<>();
    
    public ElectionState(String electionType, List<String> candidates){
        this.electionType=electionType;
        this.candidates=new ArrayList<>(candidates);
    }

    public void addPollingStation(PollingStation station){
        pollingStations.put(station.getStationId(), station);
    }
    
    public PollingStation getPollingStation(int stationId){
        return pollingStations.get(stationId);
    }
    
    public Map<Integer, PollingStation> getPollingStations(){
        return pollingStations;
    }
    
    public List<String> getCandidates(){
        return candidates;
    }
    
    public String getElectionType(){
        return electionType;
    }
    
    public void saveSnapshot(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }
    
    public static ElectionState loadSnapshot(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ElectionState) ois.readObject();
        }
    }
}