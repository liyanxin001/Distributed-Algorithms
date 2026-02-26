package com.greatfree.cluster.tpc.root;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

import org.greatfree.exceptions.RemoteReadException;



import edu.greatfree.cluster.message.ChildRequest;
import edu.greatfree.cluster.message.ClusterNotification;
import edu.greatfree.cluster.message.ClusterRequest;
import edu.greatfree.cluster.message.ClusterResponse;
import edu.greatfree.cluster.message.RootResponse;
import edu.greatfree.cluster.root.RootTask;
import edu.greatfree.framework.cluster.multicast.message.ClusterAppID;

final class TPCRootTask extends RootTask {
	
	private final static Logger log = Logger.getLogger("edu.greatfree.framework.cluster.mncs.root");
			
	@Override
	public void processNotification(ClusterNotification notification) {
		switch(notification.getAppID()) 
		{
		  case ClusterAppID.SHUTDOWN_ROOT_NOTIFICATION:
			  log.info("SHUTDOWN_ROOT_NOTIFICATION received @"+Calendar.getInstance()
			      .getTime());
			 
			  
			try {
				TPCRoot.CLUSTER().stop();
			} catch (ClassNotFoundException | IOException | InterruptedException | RemoteReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			  
			 
		   
		
		}
	}
		

	@Override
	public ClusterResponse processRequest(ClusterRequest paramClusterRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RootResponse processChildRequest(ChildRequest paramChildRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
