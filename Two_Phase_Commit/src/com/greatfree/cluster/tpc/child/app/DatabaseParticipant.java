package com.greatfree.cluster.tpc.child.app;

import org.greatfree.util.Tools;

public class DatabaseParticipant {
    private final String Key;
    private ParticipantState state;
    private boolean simulateFailure;
    
    enum ParticipantState {
        ACTIVE, PREPARED, COMMITTED, ABORTED, FAILED
    }
    
    public DatabaseParticipant() {
    	this.Key = Tools.generateUniqueKey();
        this.state = ParticipantState.ACTIVE;
        this.simulateFailure = false;   	
    }
    public DatabaseParticipant(boolean simulateFailure) {
    	this.Key = Tools.generateUniqueKey();
        this.state = ParticipantState.ACTIVE;
        this.simulateFailure = simulateFailure;
    }

    // Phase 1: The Prepare call
    public boolean prepare(String transactionId) {
        log(transactionId, "Received prepare request.");

        // Simulate a random failure or a business rule violation
        if (simulateFailure) {
            log(transactionId, "Simulating failure or vote NO. Aborting.");
            this.state = ParticipantState.FAILED;
            return false; // Vote NO
        }

        // In a real scenario, we would acquire locks and ensure we can write here.
        // For simulation, we assume we can prepare successfully.
        log(transactionId, "Prepared. Locks acquired. Voting YES.");
        this.state = ParticipantState.PREPARED;
        return true; // Vote YES
    }

    // Phase 2: The Commit call
    public void commit(String transactionId) {
        if (this.state == ParticipantState.PREPARED) {
            log(transactionId, "Committing transaction. Making changes permanent.");
            this.state = ParticipantState.COMMITTED;
            // Release locks in a real scenario.
        } else {
            log(transactionId, "ERROR: Received COMMIT but was not in PREPARED state.");
        }
    }

    // Phase 2: The Abort call
    public void abort(String transactionId) {
        if (this.state == ParticipantState.PREPARED || this.state == ParticipantState.ACTIVE) {
            log(transactionId, "Aborting transaction. Rolling back changes.");
            this.state = ParticipantState.ABORTED;
            // Release locks in a real scenario.
        } else {
            log(transactionId, "ERROR: Received ABORT but was in an unexpected state.");
        }
    }

    private void log(String transactionId, String message) {
        System.out.println("[" + Key + "] [TX: " + transactionId + "] " + message);
    }

    public ParticipantState getState() {
        return state;
    }
}
