package com.nainesh.designPatterns.Observer;

/**
 * @author Nainesh
 */
public class Main {


    public static void main(String[] args) {
        YoutubeChannel channel = new YoutubeChannel();
        Observer john = new Subscriber("John");
        Observer alice = new Subscriber("Alice");
        Observer bob = new Subscriber("Bob");

        channel.addObserver(john);
        channel.addObserver(alice);
        channel.addObserver(bob);

        channel.doAction("Testing");

    }


}
