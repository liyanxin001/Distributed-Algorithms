package com.greatfree.ring.lcr.node;

import org.greatfree.concurrency.reactive.NotificationQueueCreator;

import com.greatfree.ring.lcr.message.SendNotification;



/*    */ final class SendNotificationThreadCreator
/*    */   extends Object
/*    */   implements NotificationQueueCreator<SendNotification, SendNotificationThread>
/*    */ {
/* 19 */   public SendNotificationThread createInstance(int taskSize) { return new SendNotificationThread(taskSize); }
/*    */ }
