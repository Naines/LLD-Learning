package com.nainesh.lld.MessageBroker;

import java.util.*;
import java.util.concurrent.*;

class Message {
    String id, payload;
    long timestamp;
    Message(String id, String payload) {
        this.id = id;
        this.payload = payload;
        this.timestamp = System.currentTimeMillis();
    }
}

class Topic {
    BlockingQueue<Message> q;
    String name;
    List<Consumer> consumers = new ArrayList<>();
    ExecutorService es = Executors.newCachedThreadPool();
    Map<String, Boolean > ackMap = new ConcurrentHashMap<>();

    Topic(String name, int cap){
        this.name = name;
        q=new LinkedBlockingQueue<>(cap);
    }

    void addConsumer(Consumer consumer){
        consumers.add(consumer);
        //on adding consumer to my topic, it takes the task from blocking queue to consume
        //this thread keeps on running, unit interrupted
        es.submit(()->{
            try{
                while(!Thread.currentThread().isInterrupted()){
                    Message msg = q.take();
                    consumer.consume(msg, this);
                }
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        });
    }

    //as soon as published, consumers start acting.
    void publish(Message message) {
        try {
            ackMap.put(message.id, false);
            q.put(message); // blocks if queue is full
            System.out.println("Message published to topic " + name + ": " + message.payload);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    void acknowledge(String messageId) {
        ackMap.put(messageId, true);
        System.out.println("Ack received for: " + messageId);
    }

    void shutdown() {
        es.shutdownNow(); // stop all consumer threads
    }
}

class Broker{
    Map<String, Topic> topics = new ConcurrentHashMap<>();
    int capacity = 3;
    Topic createTopic(String name){
        Topic topic = new Topic(name, capacity);
        topics.put(name, topic);
        return topic;
    }

    Topic getTopic(String name) {
        return topics.get(name);
    }

    void shutdownAll() {
        for (Topic topic : topics.values()) {
            topic.shutdown();
        }
    }
}

//should publish the msg to said topic
class Producer{
    String producerId;
    Producer(String producerId){
        this.producerId = producerId;
    }

    void publish(Message message, Topic topic) {
        topic.publish(message);
        System.out.println("Producer " + producerId + " published: " + message.payload);
    }
}

class Consumer{
    String consumerId;
    Set<String> processed = new HashSet<>();

    Consumer(String consumerId) {
        this.consumerId = consumerId;
    }

    //defines how to consume
    void consume(Message message, Topic topic) throws InterruptedException {
//        Thread.sleep(1000);
        if(processed.contains(message.id)){
            System.out.println("Duplicated ignored");
        }
        System.out.println("Consumer " + consumerId + " consumed: " + message.payload);
        // In real-world: send ack, ensure idempotency, etc.
        processed.add(message.id);
        topic.acknowledge(message.id);
    }
}


public class Main{
    public static void main(String[] args) {
        Broker broker = new Broker();
        Topic t = broker.createTopic("order");


        Producer p = new Producer("p1");
        Consumer ca = new Consumer("c1");
        Consumer cb = new Consumer("c2");
        t.addConsumer(ca);
        t.addConsumer(cb);

        Message m1 = new Message("123", "THis is the first payload");
        Message m2 = new Message("456", "THis is the second payload");
        p.publish(m1, t);
        p.publish(m2, t);
        broker.shutdownAll();
    }
}
