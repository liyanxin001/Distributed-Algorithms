package com.greatfree.ring.lcr.node;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import org.greatfree.exceptions.RemoteReadException;

import org.greatfree.util.IPAddress;
import org.greatfree.util.Tools;


import edu.greatfree.cluster.ClusterSpec;

import edu.greatfree.container.PeerProfile;

import edu.greatfree.server.Peer;

public class Process {
	
	private static final Logger log = Logger.getLogger("edu.greatfree.cluster.child");
	
	private Peer<ProcessDispatcher> peer;
	private String UID;
    private boolean isLeader;
	private IPAddress localAddress;
	private AtomicBoolean isShutdown;
	private ClusterSpec spec;
	private final String defaultTaskKey;
	

	
	public Process(ClusterSpec spec) throws IOException {
		this.spec = spec;
		this.UID = Tools.generateUniqueKey();
		ProcessDispatcher pd = new ProcessDispatcher(this.spec.getDispatcherProfile());
		this.peer = new Peer<ProcessDispatcher>(PeerProfile.getSyncPeerProfile(this.spec, pd));
	    this.defaultTaskKey = pd.getServerKey();
	    this.setIsShutdown(new AtomicBoolean(false));
	}
	
	 public void start(String rootName, int nodeNumber, int port, String registryIP, int registryPort) throws IOException, InterruptedException, ClassNotFoundException, RemoteReadException {

		 
	 }
	 
	public void notify(String characterKey) throws IOException, InterruptedException {
		 this.peer.syncNotify(characterKey, spec.getRegistryPort(), null);
	}
	public Peer<ProcessDispatcher> getPeer() {
		return peer;
	}
	public void setPeer(Peer<ProcessDispatcher> peer) {
		this.peer = peer;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}

	

	public IPAddress getLocalIPAddress() {
		return localAddress;
	}
	public void setLocalIPAddress(IPAddress localIPAddress) {
		this.localAddress = localIPAddress;
	}




	public boolean isLeader() {
		return isLeader;
	}

	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}



	public AtomicBoolean getIsShutdown() {
		return isShutdown;
	}

	public void setIsShutdown(AtomicBoolean isShutdown) {
		this.isShutdown = isShutdown;
	}

	public String getDefaultTaskKey() {
		return defaultTaskKey;
	}

	public static Logger getLog() {
		return log;
	}

}
