package com.nainesh.lld.NotificationSystem;

import com.nainesh.lld.NotificationSystem.factory.NotificationChannelFactory;
import com.nainesh.lld.NotificationSystem.strategy.EmailNotificationStartegy;
import com.nainesh.lld.NotificationSystem.strategy.NotificationStrategy;
import com.nainesh.lld.NotificationSystem.strategy.SmsNotificationStrategy;

/**
 * @author Nainesh
 */
public class Main {


    public static void main(String[] args) {
        YoutubeChannel channel = new YoutubeChannel();

        Observer john = new Subscriber.Builder().name("john").channel("email").build();
        Observer alice = new Subscriber.Builder().name("alice").channel("sms").build();
        Observer bob = new Subscriber.Builder().name("bob").channel("push").build();

        Observer candice = new Subscriber.Builder().name("candice").strategy(NotificationChannelFactory.getStrategy("email")).build();


        channel.addObserver(alice);
        channel.addObserver(john);
        channel.addObserver(bob);
        channel.addObserver(candice);

        channel.doAction("Testing");
    }


}
