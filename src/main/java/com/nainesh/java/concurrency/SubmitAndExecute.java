package com.nainesh.java.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Nainesh
 */
public class SubmitAndExecute {
    public static void main(String[] args) throws Exception {
        ExecutorService executor =
                Executors.newFixedThreadPool(2);
        /*
         * =========================
         * execute()
         * =========================
         *
         * - Takes Runnable only
         * - No return value
         * - Cannot track result
         */
        executor.execute(() -> {
            System.out.println("execute() task by " + Thread.currentThread().getName());
        });


        /*
         * =========================
         * submit()
         * =========================
         *
         * - Takes Runnable or Callable
         * - Returns Future
         * - Can get result/status
         */
        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            return "Task completed";
        });
        System.out.println("Main thread continues...");
        /*
         * get() blocks until task completes
         */
        String result = future.get();
        System.out.println(result);
        executor.shutdown();
    }
}