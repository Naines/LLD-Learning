package com.nainesh.java.concurrency;

class SharedResource {
    private int data;
    private boolean hasData;

    public synchronized void produce(int value) {
        while (hasData) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        data = value;
        hasData = true;
        System.out.println("Produced: " + value);
        notifyAll();
    }

    public synchronized int consume() {
        while (!hasData){
            try{
//                Thread.sleep(2000);
                wait();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        hasData = false;
        System.out.println("Consumed: " + data);
        notifyAll();
        return data;
    }
}

class Producer implements Runnable {
    private SharedResource resource;

    public Producer(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            resource.produce(i);
        }
    }
}

class Consumer implements Runnable {
    private SharedResource resource;

    public Consumer(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int value = resource.consume();
        }
    }
}

public class ThreadCommunication {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        Thread producerThread = new Thread(new Producer(resource));
        Thread producerThread2 = new Thread(new Producer(resource));
        Thread producerThread3 = new Thread(new Producer(resource));
        Thread consumerThread = new Thread(new Consumer(resource));
        Thread consumerThread2 = new Thread(new Consumer(resource));
        Thread consumerThread3 = new Thread(new Consumer(resource));

        producerThread.start();
        producerThread2.start();
        producerThread3.start();
        consumerThread2.start();
        consumerThread3.start();
        consumerThread.start();
    }
}
