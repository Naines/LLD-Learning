package com.nainesh.lld.NotificationSystem.factory;


import com.nainesh.lld.NotificationSystem.strategy.EmailNotificationStartegy;
import com.nainesh.lld.NotificationSystem.strategy.NotificationStrategy;
import com.nainesh.lld.NotificationSystem.strategy.PushNotificationStrategy;
import com.nainesh.lld.NotificationSystem.strategy.SmsNotificationStrategy;

/**
 * @author Nainesh
 */
public class NotificationChannelFactory {

    public static NotificationStrategy getStrategy(String channel){
        switch (channel.toLowerCase()){
            case "email":
                return new EmailNotificationStartegy();
            case "sms":
                return new SmsNotificationStrategy();
            case "push":
                return new PushNotificationStrategy();
            default:
                throw new IllegalStateException();
        }
    }
}
