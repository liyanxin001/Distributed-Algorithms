	package com.greatfree.cluster.tpc.message;


import edu.greatfree.multicast.message.MulticastResponse;

public class PrepareResponse extends MulticastResponse{
	
	private static final long serialVersionUID = -119542231659928514L;
	private boolean vote;

	public PrepareResponse(boolean vote, String collaboratorKey) {
		super(AppID.PREPARE_RESPONSE, collaboratorKey);
		this.setVote(vote);
	}

	public boolean getVote() {
		return vote;
	}

	public void setVote(boolean vote) {
		this.vote = vote;
	}
	
	

}
