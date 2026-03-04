package com.greatfree.cluster.tpc.child;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;


import com.greatfree.cluster.tpc.child.app.Coordinator;
import com.greatfree.cluster.tpc.child.app.Participant;
import com.greatfree.cluster.tpc.message.AbortNotification;
import com.greatfree.cluster.tpc.message.AppID;
import com.greatfree.cluster.tpc.message.AssignCoordinatorNotification;
import com.greatfree.cluster.tpc.message.AssignParticipantNotification;
import com.greatfree.cluster.tpc.message.CommitNotification;
import com.greatfree.cluster.tpc.message.InterAbortNotification;
import com.greatfree.cluster.tpc.message.InterAssignParticipantNotification;
import com.greatfree.cluster.tpc.message.InterCommitNotification;
import com.greatfree.cluster.tpc.message.InterPrepareRequest;
import com.greatfree.cluster.tpc.message.PrepareRequest;
import com.greatfree.cluster.tpc.message.PrepareResponse;
import com.greatfree.cluster.tpc.message.TransferRequest;
import com.greatfree.cluster.tpc.message.TransferResponse;

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
		        return new TransferResponse(true, tr.getCollaboratorKey());
		}
        return null;
	}

	@Override
	public InterChildrenNotification prepareNotification(IntercastNotification notification) 
	{
	    switch(notification.getAppID()) 
	    {
	        case AppID.ASSIGN_PARTICIPANT_NOTIFICATION:
	    	    log.info("ASSIGN_PARTICIPANT_NOTIFICATION received @" + Calendar.getInstance().getTime());
	    	    AssignParticipantNotification apn = (AssignParticipantNotification) notification;
	    	    Coordinator.CO().addParticipant(new Participant(apn.getParticipantId(), apn.getParticipantState()));
	    	    return new InterAssignParticipantNotification(apn);
	    	    
	        case AppID.COMMIT_NOTIFICATION:
	        	log.info("COMMIT_NOTIFICATION received @" + Calendar.getInstance().getTime());
	        	CommitNotification cn = (CommitNotification) notification;
	        	return new InterCommitNotification(cn);
	        	
	        case AppID.ABORT_NOTIFICATION:
	        	log.info("ABORT_NOTIFICATION received @" + Calendar.getInstance().getTime());
	        	AbortNotification an = (AbortNotification) notification;
	        	return new InterAbortNotification(an);
	    }
		return null;
	}

	@Override
	public InterChildrenRequest prepareRequest(IntercastRequest request) 
	{
		switch(request.getAppID()) 
		{
		    case AppID.PREPARE_REQUEST:
		    	log.info("PREPARE_REQUEST received @" + Calendar.getInstance().getTime());
		    	PrepareRequest pr = (PrepareRequest) request;
		        return new InterPrepareRequest(pr);	
		}
		return null;
	}

	@Override
	public void processNotification(InterChildrenNotification notification) 
	{

		
	}

	@Override
	public void processNotification(InterChildrenNotification notification, List<String> destinationKeys) 
	{
		switch(notification.getAppID()) 
		{
		    case AppID.ASSIGN_PARTICIPANT_NOTIFICATION:
		    	log.info("ASSIGN_PARTICAIPANT_NOTIFICATION received @" + Calendar.getInstance().getTime());
		    	InterAssignParticipantNotification iapn = (InterAssignParticipantNotification) notification;
		    	AssignParticipantNotification apn = (AssignParticipantNotification) iapn.getNotification();
		    	Participant participant = Participant.PA();
		    	participant.setParticipantId(apn.getParticipantId());
		    	participant.setParticipantState(apn.getParticipantState());
		    	break;
		    	
		    case AppID.ABORT_NOTIFICATION:
		    	log.info("ABORT_NOTIFICATION received @" + Calendar.getInstance().getTime());
		    	InterAbortNotification ian = (InterAbortNotification) notification;
		    	AbortNotification an = (AbortNotification) ian.getNotification();
		    	Participant.PA().abort(an.getTransactionId());
		    	break;
		    	
		    case AppID.COMMIT_NOTIFICATION:
		    	log.info("ABORT_NOTIFICATION received @" + Calendar.getInstance().getTime());
		    	InterCommitNotification icn = (InterCommitNotification) notification;
		    	CommitNotification cn = (CommitNotification) icn.getNotification();
		    	Participant.PA().commit(cn.getTransactionId());
		    	break;  
		}
		
	}

	@Override
	public List<MulticastResponse> processRequest(InterChildrenRequest paramInterChildrenRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MulticastResponse> processRequest(InterChildrenRequest request,
			List<String> destinationKeys) 
	{
		List<MulticastResponse> responses;
		switch(request.getAppID()) 
		{
		    case AppID.PREPARE_REQUEST:
		    	log.info("PREPARE_REQUEST received" + Calendar.getInstance().getTime());
		    	InterPrepareRequest ipr = (InterPrepareRequest) request;
		    	responses = new ArrayList<MulticastResponse>();
		    	PrepareRequest pr = (PrepareRequest) ipr.getRequest();
		    	responses.add(new PrepareResponse(Participant.PA().prepare(pr.getTransactionId()),request.getCollaboratorKey()));
		    	return responses;
		}
		return null;
	}

	@Override
	public void processResponse(ClusterResponse response) {
		// TODO Auto-generated method stub
		
	}

}
