package com.greatfree.ring.lcr.process;

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

}
