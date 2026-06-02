package com.nainesh.java.ThreadCommunication;

/**
 * @author Nainesh
 * use tryLock() whenever using RenetrantLock.
 * Always use finally, With ReentrantLock, unlike synchronized, you must manually unlock:
 */
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock2 {

    private static final ReentrantLock lock1 = new ReentrantLock();
    private static final ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            lock1.lock();
//            if(lock1.tryLock()) {
                try {
                    System.out.println("T1 acquired lock1");
                    Thread.sleep(100);
                    System.out.println("T1 waiting for lock2");
                    lock2.lock();
//                    if(lock2.tryLock()) {
                        try {
                            System.out.println("T1 acquired lock2");
                        } finally {
                            lock2.unlock();
                        }
//                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock1.unlock();
                }
//            }
        });

        Thread t2 = new Thread(() -> {
            lock2.lock();
            try {
                System.out.println("T2 acquired lock2");
                Thread.sleep(100);
                System.out.println("T2 waiting for lock1");
                lock1.lock();
                try {
                    System.out.println("T2 acquired lock1");
                } finally {
                    lock1.unlock();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock2.unlock();
            }
        });
        t1.start();
        t2.start();
    }

}
