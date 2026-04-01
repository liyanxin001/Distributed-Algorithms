package com.greatfree.ring.lcr.process;

import java.io.IOException;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.TerminateSignal;

import com.greatfree.ring.RingProfile;


import edu.greatfree.exceptions.TaskAlreadyExistedException;

/*    */ final class StartProcess
/*    */ {
/*    */   public static void main(String[] args) throws ClassNotFoundException, IOException, RemoteReadException, TaskAlreadyExistedException, InterruptedException {
/* 24 */     System.out.println("process starting up ...");
/* 25 */     UnaryProcess.CLUSTER().start(RingProfile.getLightNodeSpec("Root", 8001, "192.168.1.25", 8941));
/* 26 */     System.out.println("process started ...");
/* 27 */     TerminateSignal.SIGNAL().waitTermination();

/*    */   }
/*    */ }
