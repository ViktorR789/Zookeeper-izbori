package rs.ac.fink.izbori.model;

import java.util.Map;

public class Statistics {
    public final double percentageReported;
    public final double turnoutPercentage;
    public final Map<String, Integer> resultsPerCandidate;
    public final int stationsNeedingRetry;
    public final int totalStations;
    public final int verifiedStations;
    
    public Statistics(double percentageReported, double turnoutPercentage, 
                     Map<String, Integer> resultsPerCandidate, int stationsNeedingRetry,
                     int totalStations, int verifiedStations) {
        this.percentageReported=percentageReported;
        this.turnoutPercentage=turnoutPercentage;
        this.resultsPerCandidate=resultsPerCandidate;
        this.stationsNeedingRetry=stationsNeedingRetry;
        this.totalStations=totalStations;
        this.verifiedStations=verifiedStations;
    }
}