package com.nainesh.java.concurrency.lldConcepts.correctness;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicBoolean b = new AtomicBoolean(); //this would be lock
    static void check(){
        if(b.compareAndSet(false, true)){
            System.out.println("This is safe area. Would execute thing once.");
        }else{
            System.out.println("Out of safe area");
        }
    }
    static AtomicInteger count = new AtomicInteger(0);
    static void increment(){
        count.incrementAndGet();
    }
    static int count1=0;
    static void increment1(){
        count1++;
    }
    public static void main(String[] args) throws InterruptedException {

//     DEMO 1: for atomicInteger
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 4000; i++) {
            executor.submit(()-> increment());
            executor.submit(()-> increment1());
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(count);
        System.out.println(count1);



        /// demo 2 for atomic boolean
        ExecutorService es = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            es.submit(()-> check());
        }
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MILLISECONDS);

    }

}
