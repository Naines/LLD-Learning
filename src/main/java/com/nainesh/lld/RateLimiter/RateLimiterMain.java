package com.nainesh.lld.RateLimiter;

import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiterMain {
    private final int maxTokens;
    private final long refillIntervalMillis;
    private final AtomicInteger tokens;
    private long lastRefillTimestamp;

    public RateLimiterMain(int maxTokens, long refillIntervalMillis) {
        this.maxTokens = maxTokens;
        this.refillIntervalMillis = refillIntervalMillis;

        this.tokens = new AtomicInteger(maxTokens);
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    // Try to acquire a token
    public synchronized boolean tryAcquire() {
        refillTokens();
        if (tokens.get() > 0) {
            tokens.decrementAndGet();
            return true;
        }
        return false;
    }

    // Refill tokens based on elapsed time
    private void refillTokens() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTimestamp;
        if (elapsed > refillIntervalMillis) {
            int newTokens = (int)(elapsed / refillIntervalMillis);
            int updatedTokens = Math.min(maxTokens, tokens.get() + newTokens);
            tokens.set(updatedTokens);
            lastRefillTimestamp = now;
        }
    }

    // Demo
    public static void main(String[] args) throws InterruptedException {
        RateLimiterMain limiter = new RateLimiterMain(5, 1000); // 5 requests per second
        for (int i = 0; i < 10; i++) {
            if (limiter.tryAcquire()) {
                System.out.println("Request " + i + " allowed");
            } else {
                System.out.println("Request " + i + " blocked");
            }
            Thread.sleep(200); // simulate requests every 200ms
        }
    }
}
