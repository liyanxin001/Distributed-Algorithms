package com.greatfree.ring.lcr.message;

import org.greatfree.message.ServerMessage;

public class SendNotification extends ServerMessage{
	
	
	private static final long serialVersionUID = -3336956941945726514L;
	private String UID;
	private String senderId;
	private String originalSenderId;

	public SendNotification(int UID) {
		super(LCRAppID.SEND_NOTIFICATION);
		// TODO Auto-generated constructor stub
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getOriginalSenderId() {
		return originalSenderId;
	}

	public void setOriginalSenderId(String originalSenderId) {
		this.originalSenderId = originalSenderId;
	}

}
