package com.nainesh.java.concurrency.ThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Nainesh
 */
public class CustomThreadPool {

    public static void main(String[] args) {

        // 2 + 2 queue + 1 extra thread
        ThreadPoolExecutor tp = new ThreadPoolExecutor(
                2,
                2,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.AbortPolicy());

        for(int i=1;i<=5;i++){
            int taskId = i;
            tp.submit(()->{
                System.out.println("Task "+taskId+" by "+Thread.currentThread().getName());
            });
        }
        tp.shutdownNow();
    }

//    static class RejectionPOlicy  implements
}
