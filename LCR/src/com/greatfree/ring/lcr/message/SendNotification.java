package com.greatfree.ring.lcr.message;

import java.util.Map;

import org.greatfree.message.ServerMessage;
import org.greatfree.util.IPAddress;

public class SendNotification extends ServerMessage{
	
	
	private static final long serialVersionUID = -3336956941945726514L;
	private String LeaderUID;
	private IPAddress LeaderAddress;

	private Map<String, IPAddress> leftNodeIPs;
	private boolean isFirstSent;

	public SendNotification(int UID) {
		super(LCRAppID.SEND_NOTIFICATION);
		// TODO Auto-generated constructor stub
	}

	public String getUID() {
		return LeaderUID;
	}

	public void setUID(String uID) {
		LeaderUID = uID;
	}


	public Map<String, IPAddress> getLeftNodeIPs() {
		return leftNodeIPs;
	}

	public void setLeftNodeIPs(Map<String, IPAddress> leftNodeIPs) {
		this.leftNodeIPs = leftNodeIPs;
	}

	public boolean isFirstSent() {
		return isFirstSent;
	}

	public void setFirstSent(boolean isFirstSent) {
		this.isFirstSent = isFirstSent;
	}

	public IPAddress getLeaderAddress() {
		return LeaderAddress;
	}

	public void setLeaderAddress(IPAddress leaderAddress) {
		LeaderAddress = leaderAddress;
	}

}
