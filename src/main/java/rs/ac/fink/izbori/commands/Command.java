package rs.ac.fink.izbori.commands;

public interface Command {
    String SUBMIT_VOTES = "SUBMIT_VOTES";
    
    String serialize();
}