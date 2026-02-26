package com.greatfree.cluster.tpc.message;

import java.util.List;

import edu.greatfree.cluster.message.IntercastNotification;
import edu.greatfree.cluster.message.NotificationType;

public class CommitNotification extends IntercastNotification {
	
	private static final long serialVersionUID = -8164862784047164508L;
	private String transactionId;

	public CommitNotification(String transactionId, String coordinator, List<String> participants) {
		super(NotificationType.INTER_BROADCAST_NOTIFICATION, coordinator, participants, AppID.COMMIT_NOTIFICATION);
        this.setTransactionId(transactionId);
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
