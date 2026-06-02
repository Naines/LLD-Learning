package com.nainesh.java.AsyncComm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Nainesh
 * Shows that Future is blocking but Completable Future supports chaining and combining tasks and callback.
 */
public class Example1 {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<String> future = executor.submit(() -> {
                    Thread.sleep(3000);
                    return "Task Completed";
                });
        System.out.println("Doing other work...");
        String result = future.get(); // BLOCKS
        System.out.println(result);
        executor.shutdown();
    }
}
