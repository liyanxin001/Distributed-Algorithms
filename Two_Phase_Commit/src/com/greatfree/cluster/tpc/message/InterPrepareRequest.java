package com.greatfree.cluster.tpc.message;

import edu.greatfree.cluster.message.InterChildrenRequest;


public class InterPrepareRequest extends InterChildrenRequest{
	
	private static final long serialVersionUID = -5816989379040055119L;

	public InterPrepareRequest(PrepareRequest request) {
		super(request);

	}
	

}
