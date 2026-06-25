package com.nainesh.lld.NotificationSystem.strategy;

/**
 * @author Nainesh
 */
public class EmailNotificationStartegy implements NotificationStrategy {

    @Override
    public void send(String message) {
        System.out.println("Sending via email "+message);
    }

    @Override
    public String toString(){
        return "email";
    }
}
