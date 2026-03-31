package com.greatfree.ring;

import org.greatfree.server.ServerDispatcherProfile;
import org.greatfree.util.Builder;
import org.greatfree.util.Tools;

import edu.greatfree.client.CSClient;


import edu.greatfree.container.server.ServerSpec;

/*     */ public final class RingSpec
/*     */ {
/*     */   private String rootName;
/*     */   private String rootKey;
/*     */   private String peerName;
/*     */   private ServerDispatcherProfile sdProfile;
/*     */   private ServerSpec.ServerSpecBuilder sBuilder;
/*     */   private CSClient.CSClientBuilder cBuilder;
/*     */   private String registryIP;
/*     */   private int registryPort;
/*     */   
/*     */   public RingSpec(RingSpecBuilder builder) {
/*  54 */     if (builder.getRootName() != null) {
/*     */       
/*  56 */       this.rootName = builder.getRootName();
/*     */     }
/*     */     else {
/*     */       
/*  60 */       this.rootName = builder.getPeerName();
/*     */     } 
/*  62 */     this.rootKey = Tools.getNodeKey(this.rootName);
/*  63 */     this.peerName = builder.getPeerName();
/*  64 */     this.sdProfile = builder.getDispatcherProfile();
/*  65 */     this.sBuilder = builder.getServerBuilder();
/*  66 */     this.cBuilder = builder.getClientBuilder();
/*  67 */     this.registryIP = builder.getRegistryIP();
/*  68 */     this.registryPort = builder.getRegistryPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class RingSpecBuilder
/*     */     extends Object
/*     */     implements Builder<RingSpec>
/*     */   {
/*     */     private String rootName;
/*     */     
/*     */     private String peerName;
/*     */     
/*     */     private ServerDispatcherProfile sdProfile;
/*     */     private ServerSpec.ServerSpecBuilder sBuilder;
/*     */     private CSClient.CSClientBuilder cBuilder;
/*     */     private String registryIP;
/*     */     private int registryPort;
/*     */     
/*     */     public RingSpecBuilder rootName(String rootName) {
/*  87 */       this.rootName = rootName;
/*  88 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public RingSpecBuilder peerName(String peerName) {
/*  93 */       this.peerName = peerName;
/*  94 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public RingSpecBuilder serverDispatcherProfile(ServerDispatcherProfile sdProfile) {
/*  99 */       this.sdProfile = sdProfile;
/* 100 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public RingSpecBuilder serverSpecBuilder(ServerSpec.ServerSpecBuilder sBuilder) {
/* 105 */       this.sBuilder = sBuilder;
/* 106 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public RingSpecBuilder clientBuilder(CSClient.CSClientBuilder cBuilder) {
/* 111 */       this.cBuilder = cBuilder;
/* 112 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public RingSpecBuilder registryIP(String registryIP) {
/* 117 */       this.registryIP = registryIP;
/* 118 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public RingSpecBuilder registryPort(int registryPort) {
/* 123 */       this.registryPort = registryPort;
/* 124 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     public RingSpec build() { return new RingSpec(this); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     public String getRootName() { return this.rootName; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     public String getPeerName() { return this.peerName; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     public ServerDispatcherProfile getDispatcherProfile() { return this.sdProfile; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     public ServerSpec.ServerSpecBuilder getServerBuilder() { return this.sBuilder; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     public CSClient.CSClientBuilder getClientBuilder() { return this.cBuilder; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     public String getRegistryIP() { return this.registryIP; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     public int getRegistryPort() { return this.registryPort; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   public String getRootName() { return this.rootName; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   public String getRootKey() { return this.rootKey; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 217 */   public String getPeerName() { return this.peerName; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   public String getRegistryIP() { return this.registryIP; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   public int getRegistryPort() { return this.registryPort; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public ServerDispatcherProfile getDispatcherProfile() { return this.sdProfile; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 244 */   public ServerSpec.ServerSpecBuilder getServerBuilder() { return this.sBuilder; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 249 */   public CSClient.CSClientBuilder getClientBuilder() { return this.cBuilder; }
/*     */ }