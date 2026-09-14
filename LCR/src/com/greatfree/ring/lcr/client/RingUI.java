package com.greatfree.ring.lcr.client;

import java.io.IOException;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.IPAddress;

import com.greatfree.ring.lcr.message.SendNotification;

import edu.greatfree.framework.cluster.multicast.client.ClusterClient;


final class RingUI {
	
	private IPAddress rootAddress;
	private static RingUI instance = new RingUI();
	
	
	public static RingUI R()
	{
		if(instance == null) 
		{
			instance = new RingUI();
			return instance;
		}
		else
		{
			return instance;	
		}
	
	}
	
	public void init() throws ClassNotFoundException, RemoteReadException, IOException {
		this.rootAddress = ClusterClient.MULTI().getAddress("192.168.1.25", 8001, "Root");
	}
	
	public IPAddress getRootAddress() { return this.rootAddress; }
	
	public void printMenu() {
		System.out.println("Enter '1' to start leader Election.");
	}
	
	public void execute() throws IOException, InterruptedException {
		ClusterClient.MULTI().syncNotify(this.rootAddress.getIP(), this.rootAddress.getPort(), new SendNotification());
		
	}

}
