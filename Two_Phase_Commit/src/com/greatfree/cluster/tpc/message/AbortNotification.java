package com.greatfree.cluster.tpc.message;

import java.util.List;

import edu.greatfree.cluster.message.IntercastNotification;
import edu.greatfree.cluster.message.NotificationType;

public class AbortNotification extends IntercastNotification{
	
	
	private static final long serialVersionUID = -2489632395927452525L;
	private String transactionId;
	

	public AbortNotification(String transactionId, String coordinator, List<String> participants) {
		super(NotificationType.INTER_BROADCAST_NOTIFICATION, coordinator, participants, AppID.ABORT_NOTIFICATION);
        this.setTransactionId(transactionId);
        
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	
}
