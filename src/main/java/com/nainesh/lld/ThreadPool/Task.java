package com.nainesh.lld.ThreadPool;

/**
 * @author Nainesh
 */
public class Task implements Runnable {

    private final int id;

    public Task(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Task " + id + " started by " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Task " + id + " interrupted.");
        }
//        System.out.println("Task " + id + " ended by " + Thread.currentThread().getName());
    }
}
