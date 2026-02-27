package com.greatfree.cluster.tpc.client;

import java.io.IOException;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.IPAddress;



import edu.greatfree.framework.cluster.multicast.client.ClusterClient;

final class TPCUI {
	
	private IPAddress rootAddress;
	
    private TPCUI() {
    	
    }
	private static TPCUI instance = new TPCUI(); 
	
	public static TPCUI CL()
	{
		if(instance == null) 
		{
			instance = new TPCUI();
			return instance;
		}
		else
		{
			return instance;	
		}
	
	}
	
	public void init() throws ClassNotFoundException, RemoteReadException, IOException {
		this.rootAddress = ClusterClient.MULTI().getAddress("192.168.1.25", 8941, "Root");
	}
	
	public IPAddress getRootAddress() {
		return this.rootAddress;
	}
	
	public void printMenu(String storeName) {
		 System.out.println("\n========== Menu Head ===========");
	     System.out.println("\t1) Assign a coordinator");
	     System.out.println("\t2) Assign an active participant");
	     System.out.println("\t3) Assign a failed participant");
	     System.out.println("\t4) Simulate a transaction");
	     System.out.println("\t0) Quit");
	     System.out.println("========== Menu Tail ===========\n");
	     System.out.println("Input an option:");			
	}
	

	
}