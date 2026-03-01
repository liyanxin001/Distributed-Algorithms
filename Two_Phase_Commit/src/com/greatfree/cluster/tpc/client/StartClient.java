package com.greatfree.cluster.tpc.client;

import java.io.IOException;


import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.Tools;


import edu.greatfree.framework.cluster.multicast.client.ClusterClient;

final class StartClient {

	public static void main(String[] args)throws ClassNotFoundException, RemoteReadException, IOException,InterruptedException
	{	

		
		
		
		ClusterClient.MULTI().init();
		TPCUI.CL().init();
		
		
		String optionStr;
		int option = MenuOptions.NO_OPTION;
		while(option != MenuOptions.QUIT)
		{
			TPCUI.CL().printMenu();
			
			
			
				optionStr = Tools.INPUT.nextLine();
				option = Integer.parseInt(optionStr);
				System.out.println("Your choice:" + option);
				TPCUI.CL().execute(option);

		}
		ClusterClient.MULTI().dispose();
		
	}	
}
