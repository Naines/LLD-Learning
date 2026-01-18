package com.nainesh.lld.PubSub.entities;

import java.time.Instant;

public class Message {
    Instant timestamp;
    String payload;

    public Message(String payload) {
        this.timestamp = Instant.now();
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Message{" +
                "timestamp=" + timestamp +
                ", payload='" + payload + '\'' +
                '}';
    }
}
