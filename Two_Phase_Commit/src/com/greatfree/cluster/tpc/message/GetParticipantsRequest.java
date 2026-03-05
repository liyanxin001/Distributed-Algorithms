package com.greatfree.cluster.tpc.message;

import edu.greatfree.cluster.message.ClusterRequest;


public class GetParticipantsRequest extends ClusterRequest{
	
	
	private static final long serialVersionUID = -578493838563003432L;
	private String coordinatorId;

	public GetParticipantsRequest(String coordinatorId) {
		super(coordinatorId, AppID.GET_PARTICIPANTS_REQUEST);
		this.coordinatorId = coordinatorId;
		// TODO Auto-generated constructor stub
	}

	public String getCoordinatorId() {
		return coordinatorId;
	}

	public void setCoordinatorId(String coordinatorId) {
		this.coordinatorId = coordinatorId;
	}

}
