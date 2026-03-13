package com.greatfree.ring.lcr.node;

import org.greatfree.concurrency.reactive.NotificationQueue;

import com.greatfree.ring.lcr.message.SendNotification;



/*    */ final class SendNotificationThread
/*    */   extends NotificationQueue<SendNotification>
/*    */ {
/* 18 */   public SendNotificationThread(int taskSize) { super(taskSize); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 25 */     while (!isShutdown()) {
/*    */       
/* 27 */       while (!isEmpty()) {
/*    */ 
/*    */         
/*    */         try {
/* 31 */           SendNotification notification = (SendNotification)dequeue();
/* 32 */           System.out.println(notification.getUID());
/* 33 */           disposeMessage(notification);
/*    */         }
/* 35 */         catch (InterruptedException e) {
/*    */           
/* 37 */           e.printStackTrace();
/*    */         } 
/*    */       } 
/*    */       
/*    */       try {
/* 42 */         holdOn(10000L);
/*    */       }
/* 44 */       catch (InterruptedException e) {
/*    */         
/* 46 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }
