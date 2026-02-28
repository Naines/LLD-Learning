package com.nainesh.java.concurrency;

import java.util.concurrent.locks.Lock;

class Counter {
    private int count = 0; // shared resource

    /**
     *  public synchronized void increment().The synchronized keyword
     *  in Java provides basic thread-safety but has limitations: it
     *     locks the entire method or block, leading to potential performance
     *     issues. It lacks a try-lock mechanism, causing threads to block
     *     indefinitely, increasing the risk of deadlocks. Additionally,
     *     synchronized doesn't support multiple condition variables, offering
     *     only a single monitor per object with basic wait/notify mechanisms.
     *     In contrast, explicit locks (Lock interface) offer finer-grained control,
     *     try-lock capabilities to avoid blocking, and more sophisticated thread
     *     coordination through multiple condition variables, making them more
     *     flexible and powerful for complex concurrency scenarios.
     */
    public synchronized void increment() {

            count++;
    }

    public int getCount() {
        return count;
    }
}

public class SynchronizedExample extends Thread {
    private Counter counter;

    public SynchronizedExample(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter.increment();
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        SynchronizedExample t1 = new SynchronizedExample(counter);
        SynchronizedExample t2 = new SynchronizedExample(counter);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        }catch (Exception e){

        }
        System.out.println(counter.getCount());
        // Expected: 2000, Actual will be random <= 2000. The increment method
        // in the Counter class is not synchronized. This results in a race
        // condition when both threads try to increment the count
        // variable concurrently.

        //Without synchronization, one thread might read the value of
        // count before the other thread has finished writing its
        // incremented value. This can lead to both threads reading the
        // same value, incrementing it, and writing it back,
        // effectively losing one of the increments.
    }
}