package com.greatfree.cluster.tpc.client;

import java.io.IOException;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.IPAddress;
import org.greatfree.util.Tools;

import com.greatfree.cluster.tpc.child.app.ParticipantState;
import com.greatfree.cluster.tpc.message.AssignCoordinatorNotification;
import com.greatfree.cluster.tpc.message.AssignParticipantNotification;

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
	
	public void printMenu() {
		 System.out.println("\n========== Menu Head ===========");
	     System.out.println("\t1) Assign a coordinator");
	     System.out.println("\t2) Assign an active participant");
	     System.out.println("\t3) Assign a failed participant");
	     System.out.println("\t4) Simulate a transaction");
	     System.out.println("\t0) Quit");
	     System.out.println("========== Menu Tail ===========\n");
	     System.out.println("Input an option:");			
	}
	
	public void execute(int option) throws IOException, InterruptedException 
	{
		switch(option) 
		{
		    case MenuOptions.ASSIGN_A_COORDINATOR:	
		    	System.out.println("Create an ID for this coordinator:");
		    	String coordinatorId_1 = Tools.INPUT.nextLine();
		    	ClusterClient.MULTI().syncNotify(TPCUI.CL().getRootAddress().getIP(), TPCUI.
			    		  CL().getRootAddress().getPort(), new AssignCoordinatorNotification(coordinatorId_1));
			    break;
		    
		    case MenuOptions.ASSIGN_AN_ACTIVE_PARTICIPANT:
		    	System.out.println("Greate an ID for this participant:");
		    	String participantId = Tools.INPUT.nextLine();
		    	System.out.println("Who is the coordinator?");
		    	String coordinatorId_2 = Tools.INPUT.nextLine();
		    	ClusterClient.MULTI().syncNotify(TPCUI.CL().getRootAddress().getIP(), TPCUI.
			    		  CL().getRootAddress().getPort(), new AssignParticipantNotification(coordinatorId_2, participantId, ParticipantState.ACTIVE));
			    break;
		}
		
		
	
		
	}

	
}