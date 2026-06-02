package com.nainesh.java.concurrency;

/**
 * start() vs run()
 *
 * start()
 *
 * → Creates new thread
 * → JVM schedules execution
 * → Internally calls run()
 *
 *
 * run()
 *
 * → Normal method call
 * → No new thread
 * → Executes in current thread


 *Type of thraedPool :
 * newFixedThreadPool()
 * newCachedThreadPool()
 * newSingleThreadExecutor()
 * ScheduledExecutorService
 *
 *
 */
public class Basics {
    public static void main(String[] args) {
        Threadclass thread = new Threadclass();
        thread.start();
        Runnableclass runnableclass = new Runnableclass();
        Thread thread2 = new Thread(runnableclass);
        thread2.start();

        for (; ; ) {
            System.out.println("Main");
        }
    }
}

class Threadclass extends Thread {
    @Override
    public void run() {
        for (; ; ) {
            System.out.println("Thread");
        }
    }
}
class Runnableclass implements Runnable {
    @Override
    public void run() {
        for (; ; ) {
            System.out.println("Runnable");
        }
    }
}

