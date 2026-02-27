package com.greatfree.cluster.tpc.message;



import edu.greatfree.cluster.message.ClusterNotification;

public class AssignCoordinatorNotification extends ClusterNotification{
	
	
	
	private static final long serialVersionUID = -561182464366369250L;
	private String coordinatorId;
	
	
	

	public AssignCoordinatorNotification(String coordinatorId) {
		super(coordinatorId, AppID.ASSIGN_COORDINATOR_NOTIFICATION);
        this.setCoordinatorId(coordinatorId);
	}




	public String getCoordinatorId() {
		return coordinatorId;
	}




	public void setCoordinatorId(String coordinator) {
		this.coordinatorId = coordinator;
	}
	
}