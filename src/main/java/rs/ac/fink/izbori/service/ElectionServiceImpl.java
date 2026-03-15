package rs.ac.fink.izbori.service;

import rs.ac.fink.izbori.model.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ElectionServiceImpl{
    private ElectionState state;
    
    public ElectionServiceImpl(ElectionState state){
        this.state=state;
    }
    
    public ElectionState getState(){
        return state;
    }
    
    public synchronized SubmissionResult submitVotes(VoteSubmission submission){
        PollingStation station=state.getPollingStation(submission.getStationId());
        
        if(station == null){
            return new SubmissionResult(SubmissionStatus.INVALID,"Station not found",false);
        }
        
        if(station.isBlocked()){
            return new SubmissionResult(SubmissionStatus.BLOCKED,"Station blocked after failed verification",false);
        }
        
        if(station.isVerified()){
            return new SubmissionResult(SubmissionStatus.ALREADY_VERIFIED,"Station already verified",true);
        }
        
        /*if(!submission.isValid()){
            return new SubmissionResult(SubmissionStatus.INVALID_SUM,
                "Sum of candidate votes doesn't match valid votes",false);
        }*/
        
        station.getControllerSubmissions().put(submission.getControllerId(),submission);
        
        if (station.getControllerSubmissions().size()==5){
            boolean verified =verifySubmissions(station);
            
            if (verified){
                applyVerifiedResults(station,submission);
                station.setVerified(true);
                return new SubmissionResult(SubmissionStatus.VERIFIED,"Station verified!",true);
            } else {
                station.incrementVerificationAttempts();
                if (station.getVerificationAttempts()>=2){
                    station.setBlocked(true);
                    return new SubmissionResult(SubmissionStatus.BLOCKED,
                        "Station blocked after 2 failed verifications",false);
                }
                
                station.getControllerSubmissions().clear();
                return new SubmissionResult(SubmissionStatus.RETRY_NEEDED,
                    "Submissions don't match,retry needed",false);
            }
        }
        
        return new SubmissionResult(SubmissionStatus.OK,"Submission recorded,waiting for more controllers",false);
    }
    
    private boolean verifySubmissions(PollingStation station){
        List<VoteSubmission> submissions=new ArrayList<>(station.getControllerSubmissions().values());
        
        if(submissions.size()<5) return false;
        
        
        for(int i= 0;i<submissions.size(); i++){
            int matches=1;
            for(int j=i + 1; j < submissions.size(); j++){
            	if(submissions.get(i).getTotalVoted()<=station.getRegisteredVoters() && submissions.get(j).getTotalVoted()<=station.getRegisteredVoters()) {
	            	if(submissions.get(i).isValid() && submissions.get(j).isValid()){
		                if(submissions.get(i).equals(submissions.get(j))){
		                    matches++;
		                }
	            	}    
            	}
            }
            if (matches>=3) return true; 
        }
        
        return false;
    }
    
    private void applyVerifiedResults(PollingStation station,VoteSubmission submission){
        station.setTotalVoted(submission.getTotalVoted());
        station.setInvalidVotes(submission.getInvalidVotes());
        station.getVotesPerCandidate().putAll(submission.getVotesPerCandidate());
    }
    
    public Statistics getStatistics() {
        int totalStations=state.getPollingStations().size();
        int verifiedStations=0;
        int stationsNeedingRetry= 0;
        int totalRegistered =0;
        int totalVoted=0;
        Map<String,Integer> totalResults = new HashMap<>();
        
        for (String candidate : state.getCandidates()) {
            totalResults.put(candidate,0);
        }
        
        for (PollingStation station : state.getPollingStations().values()) {
            totalRegistered+=station.getRegisteredVoters();
            
            if (station.isVerified()) {
                verifiedStations++;
                totalVoted+=station.getTotalVoted();
                
                for (Map.Entry<String,Integer> entry : station.getVotesPerCandidate().entrySet()) {
                    String candidate=entry.getKey();
                    Integer votes = entry.getValue();
                    if (votes==null) {
                        votes=0;
                    }

                    Integer currentTotal=totalResults.get(candidate);
                    if (currentTotal==null) {
                        totalResults.put(candidate, votes);
                    } else {
                        totalResults.put(candidate,currentTotal+votes);
                    }
                }
            }
            
            if (station.getVerificationAttempts()>0) {
                stationsNeedingRetry++;
            }
        }
        
        double percentageReported=totalStations>0 ? (verifiedStations*100.0/totalStations) : 0;
        double turnout = totalRegistered >0 ? (totalVoted*100.0/totalRegistered) : 0;
        
        return new Statistics(percentageReported, turnout,totalResults,stationsNeedingRetry, 
                             totalStations,verifiedStations);
    }
    
    public enum SubmissionStatus {
        OK,VERIFIED,INVALID_SUM,BLOCKED,ALREADY_VERIFIED,RETRY_NEEDED,INVALID
    }
    
    public static class SubmissionResult{
        public final SubmissionStatus status;
        public final String message;
        public final boolean verified;
        
        public SubmissionResult(SubmissionStatus status,String message,boolean verified){
            this.status=status;
            this.message=message;
            this.verified=verified;
        }
    }
    
}