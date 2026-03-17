package com.greatfree.ring.lcr.node;

import java.io.IOException;
import java.util.logging.Logger;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.server.ServerDispatcherProfile;
import org.greatfree.util.IPAddress;
import org.greatfree.util.Tools;

import edu.greatfree.client.CSClient;
import edu.greatfree.client.ClientProfile;
import edu.greatfree.container.ServerProfile;
import edu.greatfree.container.server.ServerSpec;
import edu.greatfree.server.Peer;

public class Process {
	
	private static final Logger log = Logger.getLogger("edu.greatfree.cluster.child");
	
	private Peer<ProcessDispatcher> peer;
	private String UID;
    private boolean isLeader;
	private IPAddress localAddress;
	private String registryIP;
	private int registryPort;
	private ServerDispatcherProfile sdProfile;
	private ServerSpec.ServerSpecBuilder sBuilder;
	private CSClient.CSClientBuilder cBuilder;
	
	
	private static Process instance = new Process();
	
	public static Process PR() {
		if (instance == null) {
			     
		    instance = new Process();
			return instance;
		} 
			 
			     
		return instance;
	}
	
	 public void start(String rootName, int port, String registryIP, int registryPort) throws IOException, InterruptedException, ClassNotFoundException, RemoteReadException {
		 this.setSdProfile(ServerProfile.getDispatcherProfile());
		 this.setsBuilder(ServerProfile.getLightServerBuilder(port));
		 this.setcBuilder(ClientProfile.getLightProfile());
		 this.UID = Tools.generateUniqueKey();
		 this.setLeader(false);
		 this.peer.start();
		 this.localAddress = new IPAddress(this.peer.getPeerIP(), this.peer.getPort());
		 
	 }
	 
	public void notify(String characterKey) throws IOException, InterruptedException {
		 this.peer.syncNotify(characterKey, registryPort, null);
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

	public String getRegistryIP() {
		return registryIP;
	}

	public void setRegistryIP(String registryIP) {
		this.registryIP = registryIP;
	}

	public int getRegistryPort() {
		return registryPort;
	}

	public void setRegistryPort(int registryPort) {
		this.registryPort = registryPort;
	}

	public ServerDispatcherProfile getSdProfile() {
		return sdProfile;
	}

	public void setSdProfile(ServerDispatcherProfile sdProfile) {
		this.sdProfile = sdProfile;
	}

	public ServerSpec.ServerSpecBuilder getsBuilder() {
		return sBuilder;
	}

	public void setsBuilder(ServerSpec.ServerSpecBuilder sBuilder) {
		this.sBuilder = sBuilder;
	}

	public CSClient.CSClientBuilder getcBuilder() {
		return cBuilder;
	}

	public void setcBuilder(CSClient.CSClientBuilder cBuilder) {
		this.cBuilder = cBuilder;
	}

}
