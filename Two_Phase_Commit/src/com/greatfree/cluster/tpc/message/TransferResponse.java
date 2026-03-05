package com.greatfree.cluster.tpc.message;

import edu.greatfree.multicast.message.MulticastResponse;

public class TransferResponse extends MulticastResponse{
	
	
	private static final long serialVersionUID = -206784823564235985L;
	
	private boolean isProceeding;

	public TransferResponse(boolean isSucceeded, String collaboratorKey) {
		super(AppID.TRANSFER_RESPONSE, collaboratorKey);

	}

	public boolean isProceeding() {
		return isProceeding;
	}

	public void setProceeding(boolean isSucceeded) {
		this.isProceeding = isSucceeded;
	}
	

}
