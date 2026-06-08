package com.nainesh.java.concurrency.ThreadPool;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Nainesh
 */
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        //1. fixed TP
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        /*
         * Internally:
         * new ThreadPoolExecutor(
         *     3,
         *     3,
         *     0L,
         *     TimeUnit.MILLISECONDS,
         *     new LinkedBlockingQueue<Runnable>()
         * );
         */

        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            fixedPool.submit(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("FixedPool Task " + taskId + " executed by " + Thread.currentThread().getName());
            });
        }
        fixedPool.shutdown();


        //Cached TP
        ExecutorService cachedPool = Executors.newCachedThreadPool();

        /*
         * Internally:
         * new ThreadPoolExecutor(
         *     0,
         *     Integer.MAX_VALUE,
         *     60L,
         *     TimeUnit.SECONDS,
         *     new SynchronousQueue<Runnable>()
         * );
         */

//        for (int i = 1; i <= 5; i++) {
//            int taskId = i;
//            cachedPool.submit(() -> {
//                System.out.println("CachedPool Task " + taskId + " executed by " +Thread.currentThread().getName());
//            });
//        }
        cachedPool.shutdown();

        //Scheduled Executor Service
        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(2);

        /*
         * Internally:
         * new ScheduledThreadPoolExecutor(2);
         */
//        scheduler.schedule(() -> {
//            System.out.println("Delayed task executed by " + Thread.currentThread().getName());
//            },
//            3, TimeUnit.SECONDS);
//
//        scheduler.scheduleAtFixedRate(() -> {
//            System.out.println(
//                    "Periodic task running by " +
//                            Thread.currentThread().getName()
//            );
//        }, 1, 2, TimeUnit.SECONDS);


        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        scheduler.shutdown();
    }
}