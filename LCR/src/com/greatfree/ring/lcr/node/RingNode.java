package com.greatfree.ring.lcr.node;

import java.util.logging.Logger;

import org.greatfree.util.IPAddress;

import edu.greatfree.server.Peer;

public class RingNode {
	
	private static final Logger log = Logger.getLogger("edu.greatfree.cluster.child");
	
	private Peer<NodeDispatcher> Peer;
	private int UID;
	private String nodeKey;
	private IPAddress localIPAddress;
	private String clockWiseNeighbor;
	private String counterClockWiseNeighbor;
	private boolean isStartingPoint;
	
	
	
	public Peer<NodeDispatcher> getPeer() {
		return Peer;
	}
	public void setPeer(Peer<NodeDispatcher> peer) {
		Peer = peer;
	}
	public int getUID() {
		return UID;
	}
	public void setUID(int uID) {
		UID = uID;
	}
	public String getClockWiseNeighbor() {
		return clockWiseNeighbor;
	}
	public void setClockWiseNeighbor(String clockWiseNeighbor) {
		this.clockWiseNeighbor = clockWiseNeighbor;
	}
	public String getCounterClockWiseNeighbor() {
		return counterClockWiseNeighbor;
	}
	public void setCounterClockWiseNeighbor(String counterClockWiseNeighbor) {
		this.counterClockWiseNeighbor = counterClockWiseNeighbor;
	}
	public boolean isStartingPoint() {
		return isStartingPoint;
	}
	public void setStartingPoint(boolean isStartingPoint) {
		this.isStartingPoint = isStartingPoint;
	}
	public String getNodeKey() {
		return nodeKey;
	}
	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}
	public IPAddress getLocalIPAddress() {
		return localIPAddress;
	}
	public void setLocalIPAddress(IPAddress localIPAddress) {
		this.localIPAddress = localIPAddress;
	}

}
