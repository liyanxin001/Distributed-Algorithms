package com.greatfree.ring.lcr.process;

import org.greatfree.concurrency.reactive.NotificationQueueCreator;

import com.greatfree.ring.lcr.message.LeaderNotification;

final class LeaderNotificationThreadCreator  extends Object
/*    */   implements NotificationQueueCreator<LeaderNotification, LeaderNotificationThread>
/*    */ {
/* 19 */   public LeaderNotificationThread createInstance(int taskSize) { return new LeaderNotificationThread(taskSize); }
/*    */ }