package com.greatfree.cluster.tpc.root;

import edu.greatfree.cluster.ClusterSpec;
import edu.greatfree.cluster.root.Root;
import edu.greatfree.cluster.root.RootTask;
import edu.greatfree.exceptions.NoChildrenAvailableException;
import edu.greatfree.exceptions.TaskAlreadyExistedException;
import java.io.IOException;
import org.greatfree.exceptions.RemoteReadException;
import org.greatfree.util.TerminateSignal;

final class TPCRoot
{
  private Root root;
  private static TPCRoot instance = new TPCRoot();

  
  public static TPCRoot CLUSTER() {
    if (instance == null) {
      
      instance = new TPCRoot();
      return instance;
    } 

    
    return instance;
  }


  
  public void stop() throws ClassNotFoundException, IOException, InterruptedException, RemoteReadException {
    TerminateSignal.SIGNAL().notifyAllTermination();
    this.root.stop();
  }

  
  public void start(ClusterSpec spec, RootTask... tasks) throws IOException, TaskAlreadyExistedException, ClassNotFoundException, RemoteReadException, NoChildrenAvailableException {
    this.root = new Root(spec, tasks);
    this.root.start();
  }
}
