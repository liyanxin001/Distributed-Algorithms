package com.greatfree.cluster.tpc.message;



import edu.greatfree.cluster.message.ClusterRequest;
import edu.greatfree.cluster.message.RequestType;

public class GetParticipantsRequest extends ClusterRequest {

	private static final long serialVersionUID = -1759133752348447207L;
	
	
	private String transactionId;

	public GetParticipantsRequest(String transactionId) {
		super(RequestType.BROADCAST_REQUEST);
		this.setTransactionId(transactionId);

	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	

}
