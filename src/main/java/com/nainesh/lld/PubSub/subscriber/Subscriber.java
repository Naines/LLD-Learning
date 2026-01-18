package com.nainesh.lld.PubSub.subscriber;

import com.nainesh.lld.PubSub.entities.Message;

public interface Subscriber {
    String getId();
    void onMessage(Message msg);
}
