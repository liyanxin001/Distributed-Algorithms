package com.greatfree.cluster.tpc.message;

import java.util.List;

import edu.greatfree.cluster.message.IntercastRequest;
import edu.greatfree.cluster.message.RequestType;

public class prepareRequest extends IntercastRequest{
	
	

	private static final long serialVersionUID = -4237038170415870101L;

	private String transactionId;
	
	public prepareRequest(String transactionId, String coordinator, List<String> participants) 
	{
		super(RequestType.INTER_BROADCAST_REQUEST, coordinator, participants, AppID.PREPARE_REQUEST);
        this.setTransactionId(transactionId);
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}



	


	
	

}
