package com.greatfree.ring.lcr.node;

import java.io.IOException;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.TerminateSignal;



import edu.greatfree.cluster.ClusterProfile;
import edu.greatfree.cluster.child.ChildTask;
import edu.greatfree.cluster.child.UnaryChild;
import edu.greatfree.exceptions.TaskAlreadyExistedException;

/*    */ final class StartProcess
/*    */ {
/*    */   public static void main(String[] args) throws ClassNotFoundException, IOException, RemoteReadException, TaskAlreadyExistedException, InterruptedException {
/* 24 */     System.out.println("process starting up ...");
/* 25 */     Process.PR().start(ClusterProfile.getLightChildSpec("StartingPoint", 8001, "192.168.1.25", 8941), new ChildTask[] { new MarketChildTask() });
/* 26 */     System.out.println("process started ...");
/* 27 */     TerminateSignal.SIGNAL().waitTermination();

/*    */   }
/*    */ }
