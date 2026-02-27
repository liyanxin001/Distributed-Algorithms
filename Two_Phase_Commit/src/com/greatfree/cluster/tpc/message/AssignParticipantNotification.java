package com.greatfree.cluster.tpc.message;



import com.greatfree.cluster.tpc.child.app.ParticipantState;


import edu.greatfree.cluster.message.IntercastNotification;






public class AssignParticipantNotification extends IntercastNotification{
	
	
	private static final long serialVersionUID = -2489632395927452525L;
	private String coordinator;
	private String participant;
	private ParticipantState participantState;
	

	public AssignParticipantNotification(String coordinator, String participant, ParticipantState participantState) {
		super(coordinator, participant, AppID.ASSIGN_PARTICIPANT_NOTIFICATION);
        this.setCoordinator(coordinator);
        this.setParticipant(participant);
	}


	public String getCoordinator() {
		return coordinator;
	}


	public void setCoordinator(String coordinator) {
		this.coordinator = coordinator;
	}


	public String getParticipant() {
		return participant;
	}


	public void setParticipant(String participant) {
		this.participant = participant;
	}


	public ParticipantState getParticipantState() {
		return participantState;
	}


	public void setParticipantState(ParticipantState participantState) {
		this.participantState = participantState;
	}



	
}