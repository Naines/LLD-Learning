package com.nainesh.creational.singleton;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JavaConcurrencySingleton {
    private JavaConcurrencySingleton(){
        System.out.println("Instance created");
    }

    private static JavaConcurrencySingleton instance;

    private static Lock mtx = new ReentrantLock();
    public static JavaConcurrencySingleton getInstance(){
        if(instance==null){ //first check w/o locking
            mtx.lock(); //acquire lock before creating ins
            try{
                if(instance==null){ //double checked locking
                    instance=new JavaConcurrencySingleton();
                }
            }finally {
                mtx.unlock();//always release lock
            }
        }
        return instance;
    }

    public void processPayment(double amt) {
        System.out.println(amt+ "Transferred.");
    }

    public static void main(String[] args) {
        JavaConcurrencySingleton obj = JavaConcurrencySingleton.getInstance();
        obj.processPayment(400.0);
    }
}
