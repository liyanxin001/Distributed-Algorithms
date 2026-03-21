package com.greatfree.ring.lcr.node;

import java.io.IOException;
import java.util.logging.Logger;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.IPAddress;
import org.greatfree.util.TerminateSignal;

import edu.greatfree.cluster.ClusterSpec;

import edu.greatfree.exceptions.TaskAlreadyExistedException;

/*    */ public final class UnaryProcess
/*    */ {
/* 23 */   private static final Logger log = Logger.getLogger("com.greatfree.cluster.LCR");
/*    */ 
/*    */ 
/*    */   
/*    */   private Process process;
/*    */ 
/*    */ 
/*    */   
/* 31 */   private static UnaryProcess instance = new UnaryProcess();
/*    */ 
/*    */   
/*    */   public static UnaryProcess CLUSTER() {
/* 35 */     if (instance == null) {
/*    */       
/* 37 */       instance = new UnaryProcess();
/* 38 */       return instance;
/*    */     } 
/*    */ 
/*    */     
/* 42 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void stop() throws ClassNotFoundException, IOException, InterruptedException, RemoteReadException {
/* 48 */     TerminateSignal.SIGNAL().notifyAllTermination();
/* 49 */     this.process.stop();
/*    */   }
/*    */ 
/*    */   
/*    */   public void start(ClusterSpec spec) throws ClassNotFoundException, IOException, RemoteReadException, TaskAlreadyExistedException, InterruptedException {
/* 54 */     this.process = new Process(spec);
/*    */     this.process.start();
/*    */     
/* 59 */     
/*    */       
/* 61 */    
/*    */      
/* 63 */     log.info("Process's address is " + String.valueOf(new IPAddress(this.process.getProcessIP(), this.process.getProcessPort())));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 68 */   public IPAddress getIP() { return new IPAddress(this.process.getProcessIP(), this.process.getProcessPort()); }
/*    */ 
/*    */ 
/*    */   public String getUID() {return this.process.getUID();}
}	
