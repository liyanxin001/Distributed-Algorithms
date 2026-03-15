package com.greatfree.ring.lcr.node;

import java.io.IOException;
import java.util.logging.Logger;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.IPAddress;

import edu.greatfree.cluster.ClusterSpec;
import edu.greatfree.framework.multicast.MulticastProfile;
import edu.greatfree.multicast.root.RootClient;
import edu.greatfree.server.Peer;

public class Process {
	
	private static final Logger log = Logger.getLogger("edu.greatfree.cluster.child");
	
	private Peer<ProcessDispatcher> peer;
	private int UID;
	private String successor;
	private String predecessor;
	private IPAddress localAddress;
    
	private ClusterSpec specs;
	
	private static Process instance = new Process();
	
	public static Process PR() {
		if (instance == null) {
			     
		    instance = new Process();
			return instance;
		} 
			 
			     
		return instance;
	}
	
	 public void start() throws IOException, InterruptedException, ClassNotFoundException, RemoteReadException {
		 this.peer.start();
		 this.localAddress = new IPAddress(this.peer.getPeerIP(), this.peer.getPort());
		 
	 }
	public Peer<ProcessDispatcher> getPeer() {
		return peer;
	}
	public void setPeer(Peer<ProcessDispatcher> peer) {
		this.peer = peer;
	}
	public int getUID() {
		return UID;
	}
	public void setUID(int uID) {
		UID = uID;
	}

	

	public IPAddress getLocalIPAddress() {
		return localAddress;
	}
	public void setLocalIPAddress(IPAddress localIPAddress) {
		this.localAddress = localIPAddress;
	}


	public String getPredecessor() {
		return predecessor;
	}


	public void setPredecessor(String predecessor) {
		this.predecessor = predecessor;
	}


	public String getSuccessor() {
		return successor;
	}


	public void setSuccessor(String successor) {
		this.successor = successor;
	}


	public ClusterSpec getSpecs() {
		return specs;
	}


	public void setSpecs(ClusterSpec specs) {
		this.specs = specs;
	}

}
