package com.greatfree.cluster.tpc.child.app;


import java.util.List;



public class Coordinator {
	private String coordinatorId;
    private String transactionId;
    private List<Participant> participants;
    
    
    private static Coordinator instance = new Coordinator();

	public static Coordinator CO() {
		   if (instance == null) {
		       
		     instance = new Coordinator();
		     return instance;
		   } 
		 
		     
	  return instance;
	}

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }



	public String getCoordinatorId() {
		return coordinatorId;
	}

	public void setCoordinatorId(String coordinatorId) {
		this.coordinatorId = coordinatorId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}