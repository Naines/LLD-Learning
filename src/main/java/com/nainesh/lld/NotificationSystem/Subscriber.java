package com.nainesh.lld.NotificationSystem;

import com.nainesh.lld.NotificationSystem.factory.NotificationChannelFactory;
import com.nainesh.lld.NotificationSystem.strategy.NotificationStrategy;

/**
 * @author Nainesh
 *
 * here builder can be used to simplify the build by this chaining.
 */
public class Subscriber implements Observer {
    private final String name;
    private final NotificationStrategy notificationStartegy;
    public Subscriber(Builder builder){
        this.name = builder.name;
        this.notificationStartegy = builder.strategy;
    }

    @Override
    public void update(String message) {
        System.out.println("Sending to " +this.name+" via "+this.notificationStartegy.toString());
        this.notificationStartegy.send("msg");
    }

    static class Builder{
        String name;
        NotificationStrategy strategy;
        Builder name(String name){
            this.name = name;
            return this;
        }

        Builder strategy(NotificationStrategy strategy){
            this.strategy = strategy;
            return this;
        }

        public Builder channel(String name){
            this.strategy = NotificationChannelFactory.getStrategy(name);
            return this;
        }

        Subscriber build(){
            if(name == null){
                throw new IllegalStateException("Name is required");
            }
            if(strategy == null){
                throw new IllegalStateException("strategy is required");
            }

            return new Subscriber(this);
        }
    }

}
