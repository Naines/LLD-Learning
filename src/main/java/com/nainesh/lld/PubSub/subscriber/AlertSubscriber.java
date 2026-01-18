package com.nainesh.lld.PubSub.subscriber;

import com.nainesh.lld.PubSub.entities.Message;

public class AlertSubscriber implements Subscriber{
    String id;

    public AlertSubscriber(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void onMessage(Message msg) {
        System.out.printf("!!! [ALERT - %s] : '%s' !!!%n", id, msg.getPayload());
    }
}
