package com.greatfree.ring.lcr.message;

import java.util.Map;

import org.greatfree.message.ServerMessage;
import org.greatfree.util.IPAddress;

public class SendNotification extends ServerMessage{
	
	
	private static final long serialVersionUID = -3336956941945726514L;
	private String leaderUID;
	private IPAddress leaderAddress;

	private Map<String, IPAddress> leftNodeIPs;
	private boolean isFirstSent;

	public SendNotification(String leaderUID, IPAddress leaderAddress) {
		super(LCRAppID.SEND_NOTIFICATION);
		this.leaderUID = leaderUID;
		this.leaderAddress = leaderAddress;
	}

	public String getUID() {
		return leaderUID;
	}

	public void setUID(String uID) {
		leaderUID = uID;
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
		return leaderAddress;
	}

	public void setLeaderAddress(IPAddress leaderAddress) {
		this.leaderAddress = leaderAddress;
	}

}
