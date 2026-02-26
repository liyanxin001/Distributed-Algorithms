package com.greatfree.cluster.tpc.root;

import java.io.IOException;

import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.TerminateSignal;



import edu.greatfree.cluster.ClusterProfile;
import edu.greatfree.cluster.root.RootTask;
import edu.greatfree.cluster.root.UnaryRoot;
import edu.greatfree.exceptions.NoChildrenAvailableException;
import edu.greatfree.exceptions.TaskAlreadyExistedException;

final class StartRoot
{
  public static void main(String[] args) throws ClassNotFoundException, IOException, TaskAlreadyExistedException, RemoteReadException, NoChildrenAvailableException {
    System.out.println("Root starting up ...");
    UnaryRoot.CLUSTER().start(ClusterProfile.getLightRootSpec("Root", 8000, "192.168.1.25", 8941), new RootTask[] { new TPCRootTask() });
    System.out.println("Root started ...");
    TerminateSignal.SIGNAL().waitTermination();
  }
}
