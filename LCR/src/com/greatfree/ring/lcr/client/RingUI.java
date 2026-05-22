package com.greatfree.ring.lcr.client;

import java.io.IOException;

import org.greatfree.exceptions.RemoteReadException;

import com.greatfree.ring.lcr.message.SendNotification;

import edu.greatfree.framework.cluster.multicast.client.ClusterClient;


final class RingUI {
	
	private String ip;
	private int port;
	
	
    private RingUI() {
    	
    }
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
		this.setIp("192.168.1.25");
		this.setPort(8001);
	}
	
	
	public void printMenu() {
		System.out.println("Enter '1' to start leader Election.");
	}
	
	public void execute() throws IOException, InterruptedException {
		ClusterClient.MULTI().syncNotify(this.ip, this.port, new SendNotification());
		
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
