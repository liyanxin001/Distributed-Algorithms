package com.greatfree.cluster.tpc.root;

import java.io.IOException;
import java.util.logging.Logger;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.TerminateSignal;


import edu.greatfree.cluster.ClusterSpec;
import edu.greatfree.cluster.root.Root;
import edu.greatfree.cluster.root.RootTask;

import edu.greatfree.exceptions.NoChildrenAvailableException;
import edu.greatfree.exceptions.TaskAlreadyExistedException;

/*    */ public final class TPCRoot
/*    */ {
/* 24 */   private static final Logger log = Logger.getLogger("edu.greatfree.cluster.root");
/*    */ 
/*    */ 
/*    */   
/*    */   private Root root;

/*    */ 
/*    */ 
/*    */   
/* 32 */   private static TPCRoot instance = new TPCRoot();
/*    */ 
/*    */   
/*    */   public static TPCRoot CLUSTER() {
/* 36 */     if (instance == null) {
/*    */       
/* 38 */       instance = new TPCRoot();
/* 39 */       return instance;
/*    */     } 
/*    */ 
/*    */     
/* 43 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void stop() {
/* 49 */     TerminateSignal.SIGNAL().notifyAllTermination();
/* 50 */     try {
	              this.root.stop();
             } catch (ClassNotFoundException | IOException | InterruptedException | RemoteReadException e) {
	             e.printStackTrace();
              }
/*    */   }
/*    */ 
/*    */   
/*    */   public void start(ClusterSpec spec, RootTask... tasks) throws IOException, TaskAlreadyExistedException, ClassNotFoundException, RemoteReadException, NoChildrenAvailableException {
/* 55 */     this.root = new Root(spec, tasks);
/* 56 */     this.root.start();
/* 57 */     log.info("Root's address is " + String.valueOf(this.root.getAddress()));
/*    */   }
/*    */
}
