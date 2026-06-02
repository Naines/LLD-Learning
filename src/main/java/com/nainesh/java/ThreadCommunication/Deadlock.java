package com.nainesh.java.ThreadCommunication;

/**
 * @author Nainesh
 * Thread 1 holds Lock A
 * and waits for Lock B
 *
 * Thread 2 holds Lock B
 * and waits for Lock A
 *
 * Nobody can proceed
 *
 * conditions for deadlock:
 * 1. Mutual Exclusion
 * 2. Hold and Wait
 * 3. No Preemption
 * 4. Circular Wait
 *
 * fix1: acquire locks in same order.
 * synchronized(lock1){
 *     synchronized(lock2){
 *     }
 * }
 *
 * fix2: Use tryLock() with timeout (Reentrant lock)
 * fix 3: Reduce shared mutable state. Less locking → fewer deadlocks.
 */
public class Deadlock {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args){

        Thread t1 = new Thread(() -> {
            synchronized(lock1){
                System.out.println("T1 acquired lock1");
                try{
                    Thread.sleep(100);
                }catch(Exception e){}

                System.out.println("T1 waiting for lock2");

                synchronized(lock2){
                    System.out.println("T1 acquired lock2");
                }
            }
        });


        Thread t2 = new Thread(() -> {
            synchronized(lock2){
                System.out.println("T2 acquired lock2");
                try{
                    Thread.sleep(100);
                }catch(Exception e){}
                System.out.println("T2 waiting for lock1");

                synchronized(lock1){
                    System.out.println("T2 acquired lock1");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
