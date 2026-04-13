package com.greatfree.ring.lcr.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.greatfree.client.FreeClientPool;
import org.greatfree.client.SyncRemoteEventer;
import org.greatfree.message.ServerMessage;
import org.greatfree.util.IPAddress;
import org.greatfree.util.UtilConfig;

import com.greatfree.ring.lcr.message.LeaderNotification;
import com.greatfree.ring.lcr.message.SendNotification;


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
	
	public void notify(SendNotification notification, Set<String> otherNodeKeys) throws IOException 
	{
		Map<String, String> ring = Ring.constructRing("RootKey", new ArrayList<String>(otherNodeKeys));
		HashMap<String, IPAddress> leftNodesIPs = new HashMap<String, IPAddress>();
		for(String nodeKey: ring.keySet()) 
		{
			leftNodesIPs.put(nodeKey, this.eventer.getIPAddress(nodeKey));
		}
		String nextNode = ring.get("RootKey");
		leftNodesIPs.remove(nextNode);
		notification.setLeftNodeIPs(leftNodesIPs);
		notification.setFirstSent(true);
		this.eventer.notify(nextNode, notification);	
	}
	
	public void notify(SendNotification notification) throws IOException, InterruptedException 
	{
		Map<String, IPAddress> leftNodes = notification.getLeftNodeIPs();
		if(leftNodes != UtilConfig.NO_IPS) 
		{
			Map<String, String> ring = Ring.constructRing("LocalKey", new LinkedList<String>(leftNodes.keySet()));
			String nextNode = ring.get("LocalKey");
			leftNodes.remove(nextNode);
			notification.setLeftNodeIPs(leftNodes);
			this.eventer.notify(nextNode, notification);
		}
		else {
			this.eventer.notify(notification.getLeaderAddress().getIP(), notification.getLeaderAddress().getPort(), new LeaderNotification());
		}
	}

}
