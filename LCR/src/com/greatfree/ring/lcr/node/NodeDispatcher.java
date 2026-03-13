package com.greatfree.ring.lcr.node;

import java.util.Calendar;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Logger;

import org.greatfree.concurrency.reactive.NotificationDispatcher;
import org.greatfree.message.ServerMessage;
import org.greatfree.server.MessageStream;
import org.greatfree.server.ServerDispatcher;
import org.greatfree.server.ServerDispatcherProfile;

import com.greatfree.ring.lcr.message.LCRAppID;
import com.greatfree.ring.lcr.message.SendNotification;


import edu.greatfree.container.ServerProfile;




class NodeDispatcher extends ServerDispatcher<ServerMessage>



{
	private static final Logger log = Logger.getLogger("com.greatfree.LCR.node");
	
	private NotificationDispatcher<SendNotification, SendNotificationThread, SendNotificationThreadCreator> SendNotificationDispatcher;
	
	
	public NodeDispatcher(ServerDispatcherProfile profile) {
		super(profile);
		this.SendNotificationDispatcher = new NotificationDispatcher<SendNotification, SendNotificationThread, SendNotificationThreadCreator>(ServerProfile.getNotificationDispatcherProfile(new SendNotificationThreadCreator(), getScheduler()));
	}

	
	
	


	@Override
	public void dispose(long timeout) throws InterruptedException {
		shutdown(timeout);
		this.SendNotificationDispatcher.dispose();
		
	}

	@Override
	public void process(MessageStream<ServerMessage> message) throws RejectedExecutionException {
		switch(message.getMessage().getType()) 
		{
		
		    case LCRAppID.SEND_NOTIFICATION:
		    	  log.info("CLUSTER_REQUEST received @" + String.valueOf(Calendar.getInstance().getTime()));
		    	  if (!this.SendNotificationDispatcher.isReady())
		    	  {
		    		  execute(this.SendNotificationDispatcher);
		    	  }
		    	  this.SendNotificationDispatcher.enqueue((SendNotification)message.getMessage());
		    	  break;
		    	
		}
		
	}

}
