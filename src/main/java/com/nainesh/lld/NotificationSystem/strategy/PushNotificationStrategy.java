package com.nainesh.lld.NotificationSystem.strategy;

/**
 * @author Nainesh
 */
public class PushNotificationStrategy implements NotificationStrategy{

    @Override
    public void send(String message) {
        System.out.println(" Sending via push notification "+message);
    }

    @Override
    public String toString(){
        return "push";
    }
}
