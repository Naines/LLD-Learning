package com.nainesh.lld.NotificationSystem.strategy;

/**
 * @author Nainesh
 */
public class SmsNotificationStrategy implements NotificationStrategy{
    @Override
    public void send(String message) {
        System.out.println("Sending SMS "+message);
    }

    @Override
    public String toString(){
        return "sms";
    }
}
