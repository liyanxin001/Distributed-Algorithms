package com.greatfree.cluster.tpc.message;

import java.util.List;

import edu.greatfree.cluster.message.IntercastRequest;
import edu.greatfree.cluster.message.RequestType;

public class PrepareRequest extends IntercastRequest{
	
	

	private static final long serialVersionUID = -4237038170415870101L;

	private String transactionId;
	private String coordinatorId;
	
	public PrepareRequest(String coordinatorId, List<String> participants,String transactionId) 
	{
		super(RequestType.BROADCAST_REQUEST, coordinatorId, participants, AppID.PREPARE_REQUEST);
        this.transactionId = transactionId;
        this.setCoordinatorId(coordinatorId);
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCoordinatorId() {
		return coordinatorId;
	}

	public void setCoordinatorId(String coordinatorId) {
		this.coordinatorId = coordinatorId;
	}



	


	
	

}
