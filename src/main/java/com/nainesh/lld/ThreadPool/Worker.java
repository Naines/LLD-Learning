package com.nainesh.lld.ThreadPool;

import java.util.List;

/**
 * @author Nainesh
 */

//worker is a thread which would take the task from queue.
public class Worker extends Thread {

    private final List<Runnable> q;
    private final MyThreadPool tp;
    private final long keepAliveTime;

    Worker(List<Runnable> q, MyThreadPool tp, long keepAliveTime) {
        this.q = q;
        this.tp = tp;
        this.keepAliveTime = keepAliveTime;
    }

    @Override
    public void run() {
        while (true) {
            if (tp.isShutDown() && q.isEmpty()) {
                break;
            }
            try {
                Runnable task = null;
                synchronized (q) {
                    if (!q.isEmpty()) {
                        task = q.remove(0);
                    }
                }
                if (task != null) {
                    task.run();
                } else {
                    synchronized (this) {
                        wait(keepAliveTime);
                    }
                }
            } catch (InterruptedException e) {
                if (tp.isShutDown() && q.isEmpty()) {
                    break;
                }
            } catch (RuntimeException e) {
                System.out.println(Thread.currentThread().getName() + " task failed: " + e.getMessage());
            }
        }
        System.out.println(Thread.currentThread().getName() + " terminated");
    }
}
