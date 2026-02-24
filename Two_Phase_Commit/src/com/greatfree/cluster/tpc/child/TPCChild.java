package com.greatfree.cluster.tpc.child;

import java.io.IOException;
import java.util.logging.Logger;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.IPAddress;




import edu.greatfree.cluster.ClusterSpec;
import edu.greatfree.cluster.child.Child;
import edu.greatfree.cluster.child.ChildTask;
import edu.greatfree.exceptions.RootOfflineException;
import edu.greatfree.exceptions.TaskAlreadyExistedException;










public final class TPCChild {
	
	   private static final Logger log = Logger.getLogger("edu.greatfree.cluster.child");
	 
	 
	   
	   private Child child;
	 
	 
	   
	   private static TPCChild instance = new TPCChild();
	 
	   
	   public static TPCChild CLUSTER() {
	     if (instance == null) {
	       
	       instance = new TPCChild();
	       return instance;
	     } 
	      
	     return instance;
	   }
	 
	 
	   
	   public void stop() {
	     try {
			this.child.stop();
		} catch (ClassNotFoundException | IOException | InterruptedException | RemoteReadException e) {
			e.printStackTrace();
		}
	   }
	 
	   
	   public void start(ClusterSpec spec, ChildTask... tasks) throws ClassNotFoundException, IOException, RemoteReadException, TaskAlreadyExistedException, InterruptedException {
	     this.child = new Child(spec, tasks);
	     
	     try {
	       this.child.start();
	     }
	     catch (RootOfflineException e) {
	       
	       log.info("Root is offline!");
	     } 
	     log.info("Child's address is " + String.valueOf(this.child.getAddress()));
	   }
	 
	 
	   
	   public IPAddress getIP() { return this.child.getAddress(); }
	 
	 
	   
	   public String getChildKey() { return this.child.getChildKey(); }




	

}
