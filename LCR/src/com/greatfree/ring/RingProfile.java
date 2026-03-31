package com.greatfree.ring;

import org.greatfree.util.Tools;

import edu.greatfree.client.ClientProfile;

import edu.greatfree.container.ServerProfile;
import edu.greatfree.exceptions.TaskAlreadyExistedException;

/*    */ public final class RingProfile
/*    */ {
/* 25 */   public static RingSpec getLightRootSpec(String rootName, int port, String registryIP, int registryPort) throws TaskAlreadyExistedException { return (new RingSpec.RingSpecBuilder())
/*    */       
/* 27 */       .peerName(rootName)
/* 28 */       .serverDispatcherProfile(ServerProfile.getDispatcherProfile())
/* 29 */       .serverSpecBuilder(ServerProfile.getLightServerBuilder(port))
/* 30 */       .clientBuilder(ClientProfile.getLightProfile())
/* 31 */       .registryIP(registryIP)
/* 32 */       .registryPort(registryPort)
/* 33 */       .build(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public static RingSpec getLightNodeSpec(String rootName, int port, String registryIP, int registryPort) throws TaskAlreadyExistedException { return (new RingSpec.RingSpecBuilder())
/* 41 */       .rootName(rootName)
/* 42 */       .peerName(Tools.generateUniqueKey())
/* 43 */       .serverDispatcherProfile(ServerProfile.getDispatcherProfile())
/* 44 */       .serverSpecBuilder(ServerProfile.getLightServerBuilder(port))
/* 45 */       .clientBuilder(ClientProfile.getLightProfile())
/* 46 */       .registryIP(registryIP)
/* 47 */       .registryPort(registryPort)
/* 48 */       .build(); }
/*    */ }
