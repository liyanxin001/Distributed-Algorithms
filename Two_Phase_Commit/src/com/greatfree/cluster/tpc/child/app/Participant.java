package com.greatfree.cluster.tpc.child.app;




// Class representing a single node (database) in the distributed system
public class Participant {
    private String participantId;
    private ParticipantState state;
    
    
    

    private static Participant instance = new Participant();

	public static Participant PA() {
		   if (instance == null) {
		       
		     instance = new Participant();
		     return instance;
		   } 
		 
		     
	  return instance;
	}
	
	public Participant(String participantId, ParticipantState state) {
		this.participantId = participantId;
		this.state = state;	
	}
	

    public Participant() {
		// TODO Auto-generated constructor stub
	}

	// Phase 1: The Prepare call
    public boolean prepare(String transactionId) {
        log(transactionId, "Received prepare request.");

        // Simulate a random failure or a business rule violation
        if (this.state == ParticipantState.FAILED) {
            log(transactionId, "Simulating failure. Aborting.");  
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
        System.out.println("[" + participantId + "] [TX: " + transactionId + "] " + message);
    }

    public ParticipantState getState() {
        return state;
    }
    
    public void setParticipantId(String participantId) {
    	this.participantId = participantId;
    }
    
    public void setParticipantState(ParticipantState state) {
    	this.state = state;
    }
}