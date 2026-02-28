package com.nainesh.java.concurrency;

class DaemonThreadExample {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(() -> {
            while (true) {
                System.out.println("Daemon thread running...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Daemon thread interrupted");
                }
            }
        });

        // Mark this thread as daemon
        daemonThread.setDaemon(true);
        daemonThread.start();

        // Main thread work
        System.out.println("Main thread is running...");
        try {
            Thread.sleep(3000); // main thread sleeps for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main thread finished.");
    }
}

