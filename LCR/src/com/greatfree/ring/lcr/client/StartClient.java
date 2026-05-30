package com.greatfree.ring.lcr.client;

import java.io.IOException;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.Tools;



final class StartClient {
	
	public static void main(String[] args) throws ClassNotFoundException, RemoteReadException, IOException, InterruptedException {
	
		
        RingEventer.RE().init();
        RingUI.R().init();
        
	    RingUI.R().printMenu();
	    
	    String optionStr;
		int option = -1;
		
		optionStr = Tools.INPUT.nextLine();
		option = Integer.parseInt(optionStr);
		
		while(option != 1) {
			RingUI.R().execute();
		}
	    
	}
	

	
}	
	
