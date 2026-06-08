package com.nainesh.lld.ThreadPool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Nainesh
 */
public class MyThreadPool {
    private final int core;
    private final int max;
    private final long keepAliveTime;
    private final List<Runnable> q;
    private final List<Worker> workers;
    private volatile boolean shutDown = false;

    public MyThreadPool(int core, int max, int size, long keepAliveTime) {
        if (core <= 0 || max < core || size <= 0) {
            throw new IllegalArgumentException("Invalid thread pool configuration");
        }
        this.core = core;
        this.max = max;
        this.keepAliveTime = keepAliveTime;
        this.q = new LinkedList<>();

        this.workers = new ArrayList<>();
        for (int i = 0; i < core; i++) {
            createWorker();
        }
    }

    //create a worker thread and start this.
    private void createWorker() {
        if (workers.size() >= max) {
            return;
        }
        Worker worker = new Worker(q, this, keepAliveTime);
        workers.add(worker);
        worker.start();
    }

    public void submit(Runnable task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        if (shutDown) {
            System.out.println("Task rejected: ThreadPool is shut down.");
            return;
        }
        synchronized (q) {
            q.add(task);
        }
        // Optionally, create new worker if needed
        synchronized (workers) {
            if (workers.size() < max && q.size() > workers.size()) {
                createWorker();
            }
        }
    }

    public void shutdown() {
        shutDown = true;
        for (Worker worker : workers) {
            worker.interrupt();
        }
    }

    public boolean isShutDown() {
        return shutDown;
    }
}