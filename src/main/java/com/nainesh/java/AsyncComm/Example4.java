package com.nainesh.java.AsyncComm;

/**
 * @author Nainesh
 * Callable is used when you need: return value, exceptions, async computation
 * Callable does not work directly with Thread.
 * Must use ExecutorService.
 */
import java.util.concurrent.*;

class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        return "Callable Result";
    }
}

public class Example4 {
    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new MyCallable());
        System.out.println("Doing work...");
        String result = future.get();
        System.out.println(result);
        executor.shutdown();
    }

}
