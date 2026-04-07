package com.greatfree.ring.lcr.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.greatfree.client.FreeClientPool;
import org.greatfree.client.SyncRemoteEventer;
import org.greatfree.message.ServerMessage;


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
	
	public void notify(ServerMessage notification, Set<String> nodeKeys) 
	{
		Map<String, String> ring = Ring.constructRing("RootKey", nodeKeys);
		
	}

}
