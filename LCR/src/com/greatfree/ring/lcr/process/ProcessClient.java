package com.greatfree.ring.lcr.process;

import java.io.IOException;

import java.util.HashMap;

import java.util.Map;
import java.util.Set;

import org.greatfree.client.FreeClientPool;
import org.greatfree.client.SyncRemoteEventer;
import org.greatfree.message.ServerMessage;
import org.greatfree.util.IPAddress;


final class ProcessClient 
{
	private SyncRemoteEventer<ServerMessage> eventer;
	
	public ProcessClient(FreeClientPool clientPool) {
		this.setEventer(new SyncRemoteEventer<ServerMessage>(clientPool));
	}
	
	
	public void dispose() {
		this.eventer.dispose();
	}

	public SyncRemoteEventer<ServerMessage> getEventer() {
		return eventer;
	}

	public void setEventer(SyncRemoteEventer<ServerMessage> eventer) {
		this.eventer = eventer;
	}
	
	public void notify(ServerMessage notification, Set<String> leftNodeKeys) throws IOException 
	{
		Map<String, String> ring = Ring.constructRing("RootKey", leftNodeKeys);
		HashMap<String, IPAddress> leftNodesIPs = new HashMap<String, IPAddress>();
		for(String nodeKey: ring.keySet()) {
			leftNodesIPs.put(nodeKey, this.eventer.getIPAddress(nodeKey));
		}
		String nextNode = ring.get("RootKey");
		leftNodesIPs.remove("RootKey");
		this.eventer.notify(nextNode, notification);
		
		
	}

}
