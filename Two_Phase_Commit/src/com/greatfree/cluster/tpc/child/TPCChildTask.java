package com.greatfree.cluster.tpc.child;


import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;


import com.greatfree.cluster.tpc.child.app.Coordinator;
import com.greatfree.cluster.tpc.message.AppID;
import com.greatfree.cluster.tpc.message.AssignCoordinatorNotification;
import com.greatfree.cluster.tpc.message.CommitNotification;
import com.greatfree.cluster.tpc.message.InterCommitNotification;
import com.greatfree.cluster.tpc.message.TransferRequest;

import edu.greatfree.cluster.child.ChildTask;

import edu.greatfree.cluster.message.ClusterNotification;
import edu.greatfree.cluster.message.ClusterRequest;
import edu.greatfree.cluster.message.ClusterResponse;
import edu.greatfree.cluster.message.InterChildrenNotification;
import edu.greatfree.cluster.message.InterChildrenRequest;
import edu.greatfree.cluster.message.IntercastNotification;
import edu.greatfree.cluster.message.IntercastRequest;

import edu.greatfree.multicast.message.MulticastResponse;

final class TPCChildTask extends ChildTask{
	
	private final static Logger log = Logger.getLogger("edu.greatfree.cluster.ecommerce.child");

	@Override
	public void processNotification(ClusterNotification notification) 
	{
		switch(notification.getAppID())
		{
		    case AppID.ASSIGN_COORDINATOR_NOTIFICATION:
		    	log.info("ASSIGN_COORDINATOR_NOTIFICATION recieved @" + Calendar.getInstance().getTime());
		    	AssignCoordinatorNotification acn = (AssignCoordinatorNotification) notification;
		    	Coordinator.CO().setCoordinatorId(acn.getCoordinatorId());
		    	log.info("This is the coordinator, id:" + acn.getCoordinatorId());
		    	break;
		}
	}
	@Override
	public MulticastResponse processRequest(ClusterRequest request)
	{
		switch(request.getAppID()) 
		{
		    case AppID.TRANSFER_REQUEST:
			    log.info("TRANSFER_REQUEST received @" + Calendar.getInstance().getTime());
		        TransferRequest tr = (TransferRequest) request;
		        Coordinator.CO().setTransactionId(tr.getTransactionId());
		}
        return null;
	}

	@Override
	public InterChildrenNotification prepareNotification(IntercastNotification notification) 
	{
	    switch(notification.getAppID()) 
	    {
	        case AppID.COMMIT_NOTIFICATION:
	        	log.info("COMMIT_NOTIFICATION received @" + Calendar.getInstance().getTime());
	        	CommitNotification cn = (CommitNotification) notification;
	        	return new InterCommitNotification(cn);
	    }
		return null;
	}

	@Override
	public InterChildrenRequest prepareRequest(IntercastRequest paramIntercastRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processNotification(InterChildrenNotification paramInterChildrenNotification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processNotification(InterChildrenNotification paramInterChildrenNotification, List<String> paramList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MulticastResponse> processRequest(InterChildrenRequest paramInterChildrenRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MulticastResponse> processRequest(InterChildrenRequest paramInterChildrenRequest,
			List<String> paramList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processResponse(ClusterResponse paramClusterResponse) {
		// TODO Auto-generated method stub
		
	}

}
