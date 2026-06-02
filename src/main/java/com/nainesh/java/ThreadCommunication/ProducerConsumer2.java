package com.nainesh.java.ThreadCommunication;

/**
 * @author Nainesh
 *
 * Tests:
 * Threads
 * Synchronization
 * Shared resources
 * Wait / Notify
 * Race conditions
 *
 *
 * Create:
 * Producer Thread:
 * Produces items into buffer
 *
 * Consumer Thread:
 * Consumes items from buffer
 *
 * Buffer capacity = fixed
 *
 * Producer waits when full
 * Consumer waits when empty
 *
 * Using while(q.isEMpty()) isntead of if(q.isEmpty()).
 * Saves from :
 * Thread wakes up
 * Condition may no longer be true
 * Recheck required
 *
 * This protects from:
 * Spurious wakeups
 * Multiple producers
 * Multiple consumers
 */
import java.util.concurrent.*;

public class ProducerConsumer2 {

    static int i=1;
    public static void main(String[] args){

        //bounded blocking queue
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        Thread producer = new Thread(() -> {
//                    int i = 1;
                    while(true){
                        try{
                            queue.put(i);
                            System.out.println("Produced " + i + " size=" + queue.size());
                            i++;
                            Thread.sleep(200);
                        }catch(Exception e){}
                    }
                });

        Thread consumer = new Thread(() -> {
                    while(true){
                        try{
                            int item = queue.take();
                            System.out.println("Consumed " + item);
                            Thread.sleep(800);
                        }catch(Exception e){}
                    }
                });

        producer.start();
        consumer.start();

    }

}