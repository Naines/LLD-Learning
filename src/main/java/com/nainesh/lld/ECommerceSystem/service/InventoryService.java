package com.nainesh.lld.ECommerceSystem.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class InventoryService {
    //productId -> (itemId-> count)
    final Map<String, Map<String, Integer>> inventory = new ConcurrentHashMap<>();
    //productId -> lock (per-product locking for fine-grained concurrency control)
    final Map<String, ReentrantLock> productLocks = new ConcurrentHashMap<>();
    boolean debug=true;

    public void addItems(String productId, String itemId, int count){
        // Create product lock if not exists
        productLocks.putIfAbsent(productId, new ReentrantLock());
        
        inventory.computeIfAbsent(productId, id-> new ConcurrentHashMap<>())
                .put(itemId, count);
    }
    
    void removeStock(String productId, String itemId, int count){
        ReentrantLock lock = productLocks.get(productId);
        if (lock == null) {
            throw new RuntimeException("Product not found: " + productId);
        }
        
        lock.lock();
        try {
            Map<String, Integer> itemMap = inventory.get(productId);
            if(itemMap==null) throw new RuntimeException("ProductId not found: cant decrease count.");
            int currStock = itemMap.get(itemId);
            if(currStock-count<0) throw new RuntimeException("Insufficient Stock");
            itemMap.put(itemId, currStock-count);
        } finally {
            lock.unlock();
        }
    }
    
    // NEW: Atomic remove with per-product lock (no race condition)
    void removeStockCount(String productId, int count){
        ReentrantLock lock = productLocks.get(productId);
        if (lock == null) {
            throw new RuntimeException("Product not found: " + productId);
        }
        
        lock.lock();
        try {
            Map<String, Integer> itemMap = inventory.get(productId);
            if (itemMap == null) {
                throw new RuntimeException("ProductId not found: " + productId);
            }
            
            int totalAvailable = itemMap.values().stream().mapToInt(Integer::intValue).sum();
            if (totalAvailable < count) {
                throw new RuntimeException("Insufficient Stock: requested " + count + ", available " + totalAvailable);
            }
            
            for(Map.Entry<String, Integer> e: itemMap.entrySet()){
                int currCount = e.getValue();
                if(count>currCount){
                    count-=currCount;
                    currCount=0;
                }else if(count>0 && count<currCount){
                    currCount-=count;
                    count=0;
                }
                itemMap.put(e.getKey(), currCount);
                if(count<=0) return;
            }
        } finally {
            lock.unlock();
        }
    }
    
    // NEW: Thread-safe check with read lock
    public int checkCount(String productid){
        ReentrantLock lock = productLocks.get(productid);
        if (lock == null) return 0;
        
        lock.lock();
        try {
            Map<String, Integer> itemMap = inventory.get(productid);
            if (itemMap == null) return 0;
            return itemMap.values().stream().mapToInt(Integer::intValue).sum();
        } finally {
            lock.unlock();
        }
    }

    void addStockCount(String productId, String itemId, int count){
        ReentrantLock lock = productLocks.get(productId);
        if (lock == null) {
            throw new RuntimeException("Product not found: " + productId);
        }
        
        lock.lock();
        try {
            Map<String, Integer> itemMap = inventory.get(productId);
            int currCount = itemMap.get(itemId);
            itemMap.put(itemId, currCount+count);
        } finally {
            lock.unlock();
        }
    }
}


//class InventoryService {
//    //productId -> (itemId-> count)
//    final Map<String, Map<String, Integer>> inventory = new ConcurrentHashMap<>();
//    //productId -> lock (per-product locking for fine-grained concurrency control)
//    final Map<String, ReentrantLock> productLocks = new ConcurrentHashMap<>();
//    boolean debug=true;
//
//    void addItems(String productId, String itemId, int count){
//        // Create product lock if not exists
//        productLocks.putIfAbsent(productId, new ReentrantLock());
//
//        inventory.computeIfAbsent(productId, id-> new ConcurrentHashMap<>())
//                .put(itemId, count);
//    }
//
//    // Old implementation (not atomic)
//    /*
//    void removeStock(String productId, String itemId, int count){
//        Map<String, Integer> itemMap=inventory.get(productId);
//        if(itemMap==null) throw new RuntimeException("ProductId not found: cant decrease count.");
//        int currStock = itemMap.get(itemId);
//        if(currStock-count<0) throw new RuntimeException("Insufficient Stock");
//        itemMap.put(itemId, currStock-count);
//    }
//    */
//
//    void removeStock(String productId, String itemId, int count){
//        ReentrantLock lock = productLocks.get(productId);
//        if (lock == null) {
//            throw new RuntimeException("Product not found: " + productId);
//        }
//
//        lock.lock();
//        try {
//            Map<String, Integer> itemMap = inventory.get(productId);
//            if(itemMap==null) throw new RuntimeException("ProductId not found: cant decrease count.");
//            int currStock = itemMap.get(itemId);
//            if(currStock-count<0) throw new RuntimeException("Insufficient Stock");
//            itemMap.put(itemId, currStock-count);
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    // Old implementation (not atomic - race condition between check and reserve)
//    /*
//    void removeStockCount(String productId, int count){
//        Map<String, Integer> itemMap = inventory.get(productId);
//        for(Map.Entry<String, Integer> e: itemMap.entrySet()){
//            int currCount = e.getValue();
//            if(count>currCount){
//                count-=currCount;
//                currCount=0;
//            }else if(count>0 && count<currCount){
//                currCount-=count;
//                count=0;
//            }
//            itemMap.put(e.getKey(), currCount);
//            if(count<=0) return;
//        }
//    }
//    */
//
//    // NEW: Atomic remove with per-product lock (no race condition)
//    void removeStockCount(String productId, int count){
//        ReentrantLock lock = productLocks.get(productId);
//        if (lock == null) {
//            throw new RuntimeException("Product not found: " + productId);
//        }
//
//        lock.lock();
//        try {
//            Map<String, Integer> itemMap = inventory.get(productId);
//            if (itemMap == null) {
//                throw new RuntimeException("ProductId not found: " + productId);
//            }
//
//            int totalAvailable = itemMap.values().stream().mapToInt(Integer::intValue).sum();
//            if (totalAvailable < count) {
//                throw new RuntimeException("Insufficient Stock: requested " + count + ", available " + totalAvailable);
//            }
//
//            for(Map.Entry<String, Integer> e: itemMap.entrySet()){
//                int currCount = e.getValue();
//                if(count>currCount){
//                    count-=currCount;
//                    currCount=0;
//                }else if(count>0 && count<currCount){
//                    currCount-=count;
//                    count=0;
//                }
//                itemMap.put(e.getKey(), currCount);
//                if(count<=0) return;
//            }
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    // Old implementation (not thread-safe)
//    /*
//    int checkCount(String productid){
//        Map<String, Integer> itemMap = inventory.get(productid);
//        int count=0;
//        for(Map.Entry<String, Integer> e: itemMap.entrySet()){
//            count+=e.getValue();
//        }
//        return count;
//    }
//    */
//
//    // NEW: Thread-safe check with read lock
//    int checkCount(String productid){
//        ReentrantLock lock = productLocks.get(productid);
//        if (lock == null) return 0;
//
//        lock.lock();
//        try {
//            Map<String, Integer> itemMap = inventory.get(productid);
//            if (itemMap == null) return 0;
//            return itemMap.values().stream().mapToInt(Integer::intValue).sum();
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    void addStockCount(String productId, String itemId, int count){
//        ReentrantLock lock = productLocks.get(productId);
//        if (lock == null) {
//            throw new RuntimeException("Product not found: " + productId);
//        }
//
//        lock.lock();
//        try {
//            Map<String, Integer> itemMap = inventory.get(productId);
//            int currCount = itemMap.get(itemId);
//            itemMap.put(itemId, currCount+count);
//        } finally {
//            lock.unlock();
//        }
//    }
//}

