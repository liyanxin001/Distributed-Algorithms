package com.greatfree.ring.lcr.node;

import java.util.concurrent.RejectedExecutionException;

import org.greatfree.message.ServerMessage;
import org.greatfree.server.MessageStream;
import org.greatfree.server.ServerDispatcher;




class NodeDispatcher extends ServerDispatcher<ServerMessage>
{
	
	public NodeDispatcher(int serverThreadPoolSize, long serverThreadKeepAliveTime, int schedulerPoolSize,
			long schedulerKeepAliveTime) {
		super(serverThreadPoolSize, serverThreadKeepAliveTime, schedulerPoolSize, schedulerKeepAliveTime);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void dispose(long arg0) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void process(MessageStream<ServerMessage> arg0) throws RejectedExecutionException {
		// TODO Auto-generated method stub
		
	}

}
