package com.nainesh.java.ThreadCommunication;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nainesh
 */
public class Test {
    //create 2 threads,1 producer and 1 consumer, both consume from same shared q
    public static void main(String[] args) {
        SharedMemory m = new SharedMemory(3);
        new Producer(m).start();
        new Consumer(m).start();

    }

    static class SharedMemory{
        Queue<Integer> q= new LinkedList<>();
        int max;
        SharedMemory(int max){
            this.max =max;
        }

        public synchronized void add(int x) throws InterruptedException {
            if(q.size()==max) {
                System.out.println("Q is full, waiting to remove some items...");
                wait();
            }
            q.add(x);
            System.out.println("Produced "+x);
            notifyAll();
        }

        public synchronized Integer remove() throws InterruptedException {
            if(q.isEmpty()) {
                System.out.println("My q is empty. Please wait till new produce.");
                wait();
            }
            int item  = q.poll();
            System.out.println("Consumed : " + item);
            notifyAll();
            return item;
        }
    }

    static class Producer extends Thread {

        SharedMemory m;
        Producer(SharedMemory m){
            this.m =m;
        }

        @Override
        public void run(){
            int i=1;
            while(true){
                try {
                    Thread.sleep(1500);
                    this.m.add(i++);
                } catch (InterruptedException e) {
                    System.out.println("Interrupt received saying add op is blocked.Notify consumer if blocked or sleeping." +
                            "Dont notify if threads are meant to sleep.");
//                    notifyAll();
                }
            }
        }
    }

    static class Consumer extends Thread {

        SharedMemory m;
        Consumer(SharedMemory m){
                this.m=m;
        }

        @Override
        public void run(){
            while(true){
                try {
                    Thread.sleep(2000);
                    m.remove();
                } catch (InterruptedException e) {
                    System.out.println("I received interrupt by remove method. This thread choose to wait...");
                }
            }
        }
    }


}
