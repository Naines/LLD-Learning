package com.nainesh.java.concurrency;

public class ThreadPriority extends Thread {
    public ThreadPriority(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println("Thread is Running...");
        for (int i = 1; i <= 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println(Thread.currentThread().getName() + " - Priority: " +
                        Thread.currentThread().getPriority() + " - count: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ThreadPriority l = new ThreadPriority("Low Priority Thread");
        ThreadPriority m = new ThreadPriority("Medium Priority Thread");
        ThreadPriority n = new ThreadPriority("High Priority Thread");
        l.setPriority(Thread.MIN_PRIORITY); //1
        m.setPriority(Thread.NORM_PRIORITY);//5
        n.setPriority(Thread.MAX_PRIORITY);//10
        l.start();
        m.start();
        n.start();

    }
}