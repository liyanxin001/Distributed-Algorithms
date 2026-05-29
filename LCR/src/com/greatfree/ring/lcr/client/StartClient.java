package com.greatfree.ring.lcr.client;

import java.io.IOException;

import org.greatfree.exceptions.RemoteReadException;

final class StartClient {
	
	public static void main(String[] args) throws ClassNotFoundException, RemoteReadException, IOException {
	
		
        RingEventer.RE().init();
        RingUI.R().init();
        
	    RingUI.R().printMenu();
	}
	

	
}	
	
