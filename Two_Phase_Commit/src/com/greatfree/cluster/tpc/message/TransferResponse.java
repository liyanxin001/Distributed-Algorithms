package com.greatfree.cluster.tpc.message;

import edu.greatfree.multicast.message.MulticastResponse;

public class TransferResponse extends MulticastResponse{
	
	
	private static final long serialVersionUID = -206784823564235985L;
	
	private boolean isSucceeded;

	public TransferResponse(boolean isSucceeded, String collaboratorKey) {
		super(AppID.TRANSFER_RESPONSE, collaboratorKey);
		// TODO Auto-generated constructor stub
	}

	public boolean isSucceeded() {
		return isSucceeded;
	}

	public void setSucceeded(boolean isSucceeded) {
		this.isSucceeded = isSucceeded;
	}
	

}
