package com.nainesh.java.AsyncComm;

import java.util.concurrent.CompletableFuture;

/**
 * @author Nainesh
 */
public class Example2 {
    public static void main(String[] args){

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                            try{
                                Thread.sleep(3000);
                                System.out.println("Task Finish");
                            }catch(Exception e){}
                            return "Task Completed";
                        });
        System.out.println("Doing other work...");
        future.thenAccept(System.out::println);
        System.out.println("Main thread continues");

        future.join(); //blocking to wait for complete
    }
}
