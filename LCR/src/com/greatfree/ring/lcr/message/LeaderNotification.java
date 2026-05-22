package com.greatfree.ring.lcr.message;

import org.greatfree.message.ServerMessage;

public class LeaderNotification extends ServerMessage{

	private static final long serialVersionUID = -5764029254546154279L;

	public LeaderNotification() {
		super(LCRAppID.LEADER_NOTIFICATION);

	}
	
	
	

}
