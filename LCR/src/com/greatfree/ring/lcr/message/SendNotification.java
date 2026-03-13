package com.greatfree.ring.lcr.message;

import org.greatfree.message.ServerMessage;

public class SendNotification extends ServerMessage{
	
	
	private static final long serialVersionUID = -3336956941945726514L;
	private int UID;

	public SendNotification(int UID) {
		super(LCRAppID.SEND_NOTIFICATION);
		// TODO Auto-generated constructor stub
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}

}
