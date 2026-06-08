package com.nainesh.java.ThreadCommunication;

/**
 * @author Nainesh
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

//impelment 2 runnable, producer and consumer.
//give them both a shared resource (Queue).
//the shared resource defined the implementation of add and minus.

//q1. why use notifyAll() instead of notify() ? To prevent deadlock. Notify might wake up wrong thread
//eg. Both pr and consumer are waiting. Notify might wake up wrong while notify all wakes everyone.

//q2.synchronzie -> prevent race condition.
//q3. wait() -> release lock , sleep() dont release lock
//q4: wait(), notify() -> called from inside synchronized block or method only.

public class ProducerConsumer1 {
    public static void main(String[] args){
        SharedBuffer buffer = new SharedBuffer(3);
        Thread producer = new Thread(new Producer(buffer));
        Thread consumer = new Thread(new Consumer(buffer));
        producer.start();
        consumer.start();
    }


    static class SharedBuffer {
        private Queue<Integer> buffer = new LinkedList<>();
        private int capacity;
        public SharedBuffer(int capacity){
            this.capacity = capacity;
        }
        public synchronized void produce(int item) throws InterruptedException {
            while(buffer.size() == capacity){
                System.out.println("Buffer Full. Producer Waiting");
                wait();
            }
            buffer.add(item);
            System.out.println("Produced : " + item);
            notifyAll();
        }

        public synchronized int consume() throws InterruptedException {
            if(buffer.isEmpty()){
                System.out.println("Buffer Empty. Consumer Waiting");
                wait();
            }
            int item = buffer.poll();
            System.out.println("Consumed : " + item);
            notifyAll();
            return item;
        }
    }

    static class Producer implements Runnable {
        SharedBuffer buffer; //queue
        public Producer(SharedBuffer buffer){
            this.buffer = buffer;
        }
        @Override
        public void run(){
            int value = 1;
            while(true){
                try{
                    buffer.produce(value++); //add item to queue
                    Thread.sleep(1000);
                }catch(Exception e){}
            }
        }
    }

    static class Consumer implements Runnable {
        SharedBuffer buffer;
        public Consumer(SharedBuffer buffer){
            this.buffer = buffer;
        }
        @Override
        public void run(){
            while(true){
                try{
                    buffer.consume(); //remove item from queue
//                    Thread.sleep(2000);
                }catch(Exception e){}
            }
        }
    }


}