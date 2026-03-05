package com.greatfree.cluster.tpc.message;

import java.util.List;



import edu.greatfree.multicast.message.MulticastResponse;

public class GetParticipantsResponse extends MulticastResponse{
	
	
	private static final long serialVersionUID = -5495182750849006733L;
	private List<String> participants;

	public GetParticipantsResponse(List<String> participants, String collaboratorKey) {
		super(AppID.GET_PARTICIPANTS_RESPONSE, collaboratorKey);
		this.setParticipants(participants);
		// TODO Auto-generated constructor stub
	}

	public List<String> getParticipants() {
		return participants;
	}

	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}

}
