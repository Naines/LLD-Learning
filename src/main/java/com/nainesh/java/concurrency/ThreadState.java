package com.nainesh.java.concurrency;

public class ThreadState extends Thread{
    public void run(){
        System.out.println("here");
        try {
            ThreadState.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadState t1 = new ThreadState();
        System.out.println("main");

        System.out.println(t1.getState()); // NEW
        t1.start();

        System.out.println(t1.getState()); // RUNNABLE
        Thread.sleep(100);

        System.out.println(t1.getState()); // TIMED_WAITING
        t1.join();

        System.out.println(t1.getState()); // TERMINATED
    }
}
