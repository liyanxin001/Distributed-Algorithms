package com.greatfree.cluster.tpc.message;

import java.util.List;

import edu.greatfree.cluster.message.IntercastRequest;
import edu.greatfree.cluster.message.RequestType;

public class prepareRequest extends IntercastRequest{
	
	

	private static final long serialVersionUID = -4237038170415870101L;
	private String coordinator;
	private List<String> participants;
	
	
	public prepareRequest(String coordinator, List<String> participants) 
	{
		super(RequestType.INTER_BROADCAST_REQUEST, coordinator, participants, AppID.PREPARE_REQUEST);
		this.setCoordinator(coordinator);
		this.setParticipants(participants);
	}


	public String getCoordinator() {
		return coordinator;
	}


	public void setCoordinator(String coordinator) {
		this.coordinator = coordinator;
	}


	public List<String> getParticipants() {
		return participants;
	}


	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
	


	
	

}
