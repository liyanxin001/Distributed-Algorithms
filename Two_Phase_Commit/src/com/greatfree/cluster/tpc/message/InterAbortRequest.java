package com.greatfree.cluster.tpc.message;

import edu.greatfree.cluster.message.InterChildrenNotification;


public class InterAbortRequest extends InterChildrenNotification{

	private static final long serialVersionUID = -3692044371915767564L;

	public InterAbortRequest(AbortNotification notification) {
		super(notification);
		// TODO Auto-generated constructor stub
	}
	
	

}
