package com.greatfree.ring.lcr.client;

import org.greatfree.client.FreeClientPool;
import org.greatfree.client.SyncRemoteEventer;

import com.greatfree.ring.lcr.message.SendNotification;

final class RingEventer 
{
	
	private SyncRemoteEventer<SendNotification> eventer;
	private FreeClientPool clientPool;
	private static RingEventer instance = new RingEventer();
	
	
	public static RingEventer RE() {
		if(instance == null) {
			
			instance = new RingEventer();
			return instance;
		}
		
		
		return instance;
	}
	
	public void dispose() {
		this.eventer.dispose();
		this.clientPool.dispose();
	}

	public void init() {
		this.clientPool = new FreeClientPool(10);
	}
}
