package com.greatfree.cluster.tpc.child;

import java.io.IOException;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.TerminateSignal;



import edu.greatfree.cluster.ClusterProfile;
import edu.greatfree.cluster.child.ChildTask;
import edu.greatfree.cluster.child.UnaryChild;
import edu.greatfree.exceptions.TaskAlreadyExistedException;

/*    */ final class StartChild
/*    */ {
/*    */   public static void main(String[] args) throws ClassNotFoundException, IOException, RemoteReadException, TaskAlreadyExistedException, InterruptedException {
/* 24 */     System.out.println("Child starting up ...");
/* 25 */     UnaryChild.CLUSTER().start(ClusterProfile.getLightChildSpec("Root", 8001, "192.168.1.25", 8941), new ChildTask[] { new TPCChildTask() });
/* 26 */     System.out.println("Child started ...");
/* 27 */     TerminateSignal.SIGNAL().waitTermination();
/*    */   }
/*    */ }
