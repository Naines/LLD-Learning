package com.nainesh.java.concurrency;

import java.util.concurrent.*;
import java.util.*;

class Connection {
    private final int id;
    public Connection(int id) {
        this.id = id;
    }
    public void execute(String query) {
        System.out.println(Thread.currentThread().getName() +
                " executing on Connection-" + id + ": " + query);
        try {
            Thread.sleep(1000); // simulate query execution
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public int getId() {
        return id;
    }
}


class ConnectionPool {
    private final BlockingQueue<Connection> pool;
    private final int maxConnections;

    public ConnectionPool(int size) {
        this.maxConnections = size;
        pool = new ArrayBlockingQueue<>(size);
        for (int i = 1; i <= size; i++) {
            pool.offer(new Connection(i));
        }
    }

    public Connection acquireConnection() throws InterruptedException {
        Connection conn = pool.take();
        System.out.println(Thread.currentThread().getName() +
                " acquired Connection-" + conn.getId() +
                " | Allowed connections left: " + pool.size());
        return conn;
    }

    public void releaseConnection(Connection conn) {
        pool.offer(conn);
        System.out.println(Thread.currentThread().getName() +
                " released Connection-" + conn.getId() +
                " | Allowed connections left: " + pool.size());
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public int getAllowedConnections() {
        return pool.size();
    }
}


public class ConnectionPoolDemo {
    public static void main(String[] args) {
        int poolSize = 5;
        ConnectionPool connectionPool = new ConnectionPool(poolSize);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 10; i++) {
            final int queryId = i;
            executor.submit(() -> {
                try {
                    Connection conn = connectionPool.acquireConnection();
                    conn.execute("SELECT * FROM table WHERE id=" + queryId);
                    connectionPool.releaseConnection(conn);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }
}
