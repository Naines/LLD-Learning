package com.nainesh.java.concurrency;

import java.util.concurrent.Semaphore;

class SharedPrinter {
    private final Semaphore semaphore = new Semaphore(2); // allow 2 threads at a time

    public void print(String document) {
        try {
            semaphore.acquire(); // acquire a permit
            System.out.println(Thread.currentThread().getName() + " printing: " + document);
            Thread.sleep(1000); // simulate printing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName() + " finished printing.");
            semaphore.release(); // release the permit
        }
    }
}

public class SemaphoreExample {
    public static void main(String[] args) {
        SharedPrinter printer = new SharedPrinter();

        // Create 5 threads trying to print simultaneously
        for (int i = 1; i <= 5; i++) {
            final String doc = "Document-" + i;
            new Thread(() -> printer.print(doc), "Thread-" + i).start();
        }
    }
}
