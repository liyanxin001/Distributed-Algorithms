package com.greatfree.ring.lcr.process;

import java.io.IOException;
import java.util.logging.Logger;

import com.greatfree.ring.lcr.message.SendNotification;



public final class ServiceProvider {
	
	public static final Logger log = Logger.getLogger("com.greatfree.ring.lcr");
	
	private static Process process;
	
	
	public static void processNotification(SendNotification notification) throws IOException 
	{
		process.processNotification(notification);
	}
	public static Process getPorcess() {
		return process;
	}

	public static void setPorcess(Process porcess) {
		ServiceProvider.process = porcess;
	}

}
