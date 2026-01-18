package com.nainesh.lld.PubSub;

import com.nainesh.lld.PubSub.entities.Message;
import com.nainesh.lld.PubSub.entities.Topic;
import com.nainesh.lld.PubSub.subscriber.Subscriber;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PubSubSystem {
    private final ExecutorService deliveryExecutor;
    private final Map<String, Topic> topicRegistry;

    public PubSubSystem(){
        topicRegistry = new ConcurrentHashMap<>();
        this.deliveryExecutor = Executors.newCachedThreadPool();
    }

    void createTopic(String topicName){
        topicRegistry.putIfAbsent(topicName, new Topic(topicName, deliveryExecutor));
        System.out.println("Topic " + topicName + " created");
    }

    void subscribe(String topicName, Subscriber subscriber){
        Topic topic = topicRegistry.get(topicName);
        if(topic==null) throw new IllegalArgumentException("Topic Not found"+topicName);

        topic.addSubscriber(subscriber);
        System.out.println("Subscriber '" + subscriber.getId() + "' subscribed to topic: " + topicName);
    }

    public void unsubscribe(String topicName, Subscriber subscriber) {
        Topic topic = topicRegistry.get(topicName);
        if (topic != null)
            topic.removeSubscriber(subscriber);
        System.out.println("Subscriber '" + subscriber.getId() + "' unsubscribed from topic: " + topicName);
    }

    public void publish(String topicName, Message message) {
        System.out.println("Publishing message to topic: " + topicName);
        Topic topic = topicRegistry.get(topicName);
        if (topic == null) throw new IllegalArgumentException("Topic not found: " + topicName);
        topic.broadcast(message);
    }
    public void shutdown() {
        System.out.println("PubSubService shutting down...");
        deliveryExecutor.shutdown();
        try {
            // Wait a reasonable time for existing tasks to complete
            if (!deliveryExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                deliveryExecutor.shutdownNow();
            }
        } catch (InterruptedException ie) {
            deliveryExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("PubSubService shutdown complete.");
    }


}
