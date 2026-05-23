package com.greatfree.ring.lcr.process;



import org.greatfree.concurrency.reactive.NotificationQueue;

import com.greatfree.ring.lcr.message.LeaderNotification;

public class LeaderNotificationThread   extends NotificationQueue<LeaderNotification>
{
public LeaderNotificationThread(int taskSize) {
		super(taskSize);

	}

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
/* 31 */           LeaderNotification notification = (LeaderNotification)dequeue();
                   ServiceProvider.processNotification(notification);
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

