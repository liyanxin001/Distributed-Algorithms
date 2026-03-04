package com.greatfree.cluster.tpc.client;

import java.io.IOException;
import java.util.List;

import org.greatfree.exceptions.NullClassConversionException;
import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.IPAddress;
import org.greatfree.util.Tools;

import com.greatfree.cluster.ecommerce.client.ClusterUI;
import com.greatfree.cluster.ecommerce.message.AddToCartRequest;
import com.greatfree.cluster.ecommerce.message.AddToCartResponse;
import com.greatfree.cluster.ecommerce.message.CreateStoreResponse;
import com.greatfree.cluster.tpc.child.app.ParticipantState;
import com.greatfree.cluster.tpc.message.AssignCoordinatorNotification;
import com.greatfree.cluster.tpc.message.AssignParticipantNotification;
import com.greatfree.cluster.tpc.message.PrepareRequest;
import com.greatfree.cluster.tpc.message.PrepareResponse;
import com.greatfree.cluster.tpc.message.TransferRequest;
import com.greatfree.cluster.tpc.message.TransferResponse;

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
	
	public void execute(int option) throws IOException, InterruptedException, ClassNotFoundException, RemoteReadException, NullClassConversionException 
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
		    	System.out.println("Create an ID for this active participant:");
		    	String participantId_2 = Tools.INPUT.nextLine();
		    	System.out.println("Who is its coordinator?");
		    	String coordinatorId_2 = Tools.INPUT.nextLine();
		    	ClusterClient.MULTI().syncNotify(TPCUI.CL().getRootAddress().getIP(), TPCUI.
			    		  CL().getRootAddress().getPort(), new AssignParticipantNotification(coordinatorId_2, participantId_2, ParticipantState.ACTIVE));
			    break;
			    
		    case MenuOptions.ASSIGN_A_FAILED_PARTICIPANT:
		    	System.out.println("Create an ID for this failed participant:");
		    	String participantId_3 = Tools.INPUT.nextLine();
		    	System.out.println("Who is its coordinator?");
		    	String coordinatorId_3 = Tools.INPUT.nextLine();
		    	ClusterClient.MULTI().syncNotify(TPCUI.CL().getRootAddress().getIP(), TPCUI.
		    			  CL().getRootAddress().getPort(), new AssignParticipantNotification(coordinatorId_3, participantId_3, ParticipantState.FAILED));
		    	break;
		    	
		    case MenuOptions.SIMULATE_A_TRANSACTION:
		    	System.out.println("Enter simuluated transaction ID:");
		    	String transactionId = Tools.INPUT.nextLine();
		    	System.out.println("Enter coordinator ID:");
		    	String coordinatorId_4 = Tools.INPUT.nextLine();
		    	List<TransferResponse> tr = ClusterClient.MULTI().read(TPCUI.CL().getRootAddress().getIP(),
			    		 TPCUI.CL().getRootAddress().getPort(), new TransferRequest(transactionId, coordinatorId_4),
			    		 TransferResponse.class); 
		    	for(TransferResponse entry : tr)
		    	 {
		    		 System.out.println("Transaction proceeding:" + entry.isSucceeded());
		    		 break; 
		    	 }
		    	List<PrepareResponse> pr = ClusterClient.MULTI().read(TPCUI.CL().getRootAddress().getIP(),
			    		 TPCUI.CL().getRootAddress().getPort(), new PrepareRequest(transactionId, coordinatorId_4),
			    		 PrepareResponse.class); ;
		    	 break;
               
                
		}
		
		
	
		
	}

	
}