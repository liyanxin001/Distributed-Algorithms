package com.greatfree.cluster.tpc.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.greatfree.exceptions.NullClassConversionException;
import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.IPAddress;
import org.greatfree.util.Tools;



import com.greatfree.cluster.tpc.child.app.ParticipantState;
import com.greatfree.cluster.tpc.message.AbortNotification;
import com.greatfree.cluster.tpc.message.AssignCoordinatorNotification;
import com.greatfree.cluster.tpc.message.AssignParticipantNotification;
import com.greatfree.cluster.tpc.message.CommitNotification;
import com.greatfree.cluster.tpc.message.GetParticipantsRequest;
import com.greatfree.cluster.tpc.message.GetParticipantsResponse;
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
		    		 System.out.println("Transaction proceeding:" + entry.isProceeding());
		    		 break; 
		    	 }
		    	List<GetParticipantsResponse> gpr = ClusterClient.MULTI().read(TPCUI.CL().getRootAddress().getIP(),
			    		 TPCUI.CL().getRootAddress().getPort(), new GetParticipantsRequest(coordinatorId_4),
			    		 GetParticipantsResponse.class); 
		    	List<PrepareResponse> pr = ClusterClient.MULTI().read(TPCUI.CL().getRootAddress().getIP(),
			    		 TPCUI.CL().getRootAddress().getPort(), new PrepareRequest(coordinatorId_4, gpr.get(0).getParticipants(), transactionId),
			    		 PrepareResponse.class); 
		    	List<Boolean> votes = new ArrayList<>();
		    	for(PrepareResponse entry : pr)
		    	{
		    		votes.add(entry.getVote());
		    	}
		    	if(votes.contains(false)) 
		    	{
		    		ClusterClient.MULTI().syncNotify(TPCUI.CL().getRootAddress().getIP(), TPCUI.
			    			  CL().getRootAddress().getPort(), new AbortNotification(transactionId, coordinatorId_4, gpr.get(0).getParticipants()));
		    		System.out.println("Not all participants voted YES, transaction aborted.");
		    	}
		    	else 
		    	{
		    		ClusterClient.MULTI().syncNotify(TPCUI.CL().getRootAddress().getIP(), TPCUI.
			    			  CL().getRootAddress().getPort(), new CommitNotification(transactionId, coordinatorId_4, gpr.get(0).getParticipants()));
		    		System.out.println("All participants voted YES, transaction commited");
		    	}
		    	break;
               
                
		}
		
		
	
		
	}

	
}