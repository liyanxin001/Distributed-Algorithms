package com.greatfree.cluster.tpc.message;



import com.greatfree.cluster.tpc.child.app.ParticipantState;


import edu.greatfree.cluster.message.IntercastNotification;






public class AssignParticipantNotification extends IntercastNotification{
	
	
	private static final long serialVersionUID = -2489632395927452525L;
	private String coordinatorId;
	private String participantId;
	private ParticipantState participantState;
	

	public AssignParticipantNotification(String coordinator, String participant, ParticipantState participantState) {
		super(coordinator, participant, AppID.ASSIGN_PARTICIPANT_NOTIFICATION);
        this.setCoordinatorId(coordinator);
        this.setParticipantId(participant);
	}


	public String getCoordinatorId() {
		return coordinatorId;
	}


	public void setCoordinatorId(String coordinator) {
		this.coordinatorId = coordinator;
	}


	public String getParticipantId() {
		return participantId;
	}


	public void setParticipantId(String participant) {
		this.participantId = participant;
	}


	public ParticipantState getParticipantState() {
		return participantState;
	}


	public void setParticipantState(ParticipantState participantState) {
		this.participantState = participantState;
	}



	
}