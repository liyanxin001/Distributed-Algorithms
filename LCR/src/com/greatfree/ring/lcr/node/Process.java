package com.greatfree.ring.lcr.node;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.framework.container.p2p.message.LeaveClusterNotification;
import org.greatfree.util.IPAddress;
import org.greatfree.util.Tools;

import com.greatfree.ring.RingSpec;



import edu.greatfree.container.PeerProfile;

import edu.greatfree.server.Peer;

public class Process {
	
	private static final Logger log = Logger.getLogger("edu.greatfree.cluster.child");
	
	private Peer<ProcessDispatcher> peer;
	private String UID;
    private boolean isLeader;
	private IPAddress localAddress;
	private AtomicBoolean isShutdown;
	private RingSpec spec;
	private final String defaultTaskKey;
	

	
	public Process(RingSpec ringSpec) throws IOException {
		this.spec = ringSpec;
		this.UID = Tools.generateUniqueKey();
		ProcessDispatcher pd = new ProcessDispatcher(this.spec.getDispatcherProfile());
		this.peer = new Peer<ProcessDispatcher>(PeerProfile.getSyncPeerProfile(spec.getPeerName(), spec.getServerBuilder().getPort(), spec.getRegistryIP(), spec.getRegistryPort(), pd));
	    this.defaultTaskKey = pd.getServerKey();
	    this.setIsShutdown(new AtomicBoolean(false));
	    
	}
	
	public void start() throws IOException, InterruptedException, ClassNotFoundException, RemoteReadException {
		this.peer.start();
	    this.localAddress = new IPAddress(this.peer.getPeerIP(), this.peer.getPort());	 
	}
	
	public void stop() throws IOException, InterruptedException, ClassNotFoundException, RemoteReadException {
		this.isShutdown.set(true);
		this.peer.syncNotify(this.spec.getRegistryIP(), this.spec.getRegistryPort(), new LeaveClusterNotification(this.spec.getRootKey(), this.peer.getPeerID()));
		this.peer.stop(this.spec.getServerBuilder().getShutdownServerTimeout());
	}
	 
	public void notify(String characterKey) throws IOException, InterruptedException {
		this.peer.syncNotify(characterKey, spec.getRegistryPort(), null);
	}
	
	
	public int getProcessPort() {
		return this.peer.getPort();
	}
	
	public String getProcessIP() {
		return this.peer.getPeerIP();
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
