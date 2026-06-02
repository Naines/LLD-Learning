package com.nainesh.java.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 6; i++) {
            executor.submit(new WorkerTask(i));
        }
        executor.shutdown();
    }

    static class WorkerTask implements Runnable {
        private final int taskId;
        public WorkerTask(int taskId) {
            this.taskId = taskId;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " executing task " + taskId);
            try {
                Thread.sleep(1000); // simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(Thread.currentThread().getName() + " finished task " + taskId);
        }
    }

}
