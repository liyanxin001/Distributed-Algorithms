package com.greatfree.cluster.tpc.message;



import edu.greatfree.cluster.message.ClusterRequest;

public class TransferRequest extends ClusterRequest{
	
	
	private static final long serialVersionUID = -215038106311712765L;
	private String transactionId;
	private String coordinatorId;
    
	public TransferRequest(String coordinator, String transactionId) {
		super(coordinator, AppID.TRANSFER_REQUEST);
		this.setCoordinatorId(coordinator);
		this.setTransactionId(transactionId);
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

	public void setCoordinatorId(String coordinator) {
		this.coordinatorId = coordinator;
	}

}
