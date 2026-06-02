package com.nainesh.java.AsyncComm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Nainesh
 */
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable executing");
    }
}

public class Example3 {
    public static void main(String[] args){
        Thread thread = new Thread(new MyRunnable());
        thread.start();
        System.out.println("here");


        Runnable task = () -> {
            System.out.println("Running task");
        };
        new Thread(task).start();

        new Thread(()-> System.out.println("way 3")).start();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(task);
        executor.shutdown();


    }
}
