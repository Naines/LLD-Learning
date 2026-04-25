package com.nainesh.lld.kvTTL;


import com.sun.jdi.Value;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Cache<K, V>{
    final Map<K, Entry<V>> store =new ConcurrentHashMap<>();

    public void put(K key, V value, long ttl){
        long expiry = System.currentTimeMillis()+ttl;
        store.put(key, new Entry<>(value, expiry));
    }

    public V get(K key){
        if(store.get(key) == null) return null;
        if(store.get(key).isExpired()){
            store.remove(key);
            return null;
        }
        return store.get(key).value;
    }

    void remove(K key){
        if(store.get(key)!=null){
            store.remove(key);
        }
    }

    boolean containsKey(K key){
        Entry<V> entry = store.get(key);
        if(entry==null) return false;
        return !entry.isExpired();
    }

    int size(){
        int count = 0;
        for(Map.Entry<K, Entry<V>> e: store.entrySet()){
            if(e.getValue().isExpired()){
                count++;
            }else{
                store.remove(e.getKey());
            }
        }
        return count;
    }

    static class Entry<V>{
        V value;
        long expiryTime;
        Entry(V value, long expiryTime){
            this.value = value;
            this.expiryTime = expiryTime;
        }

        boolean isExpired(){
            return System.currentTimeMillis()>expiryTime;
        }
    }
}
public class Main {
    public static void main(String[] args) throws Exception{
        Cache<String, String> cache = new Cache<>();
        cache.put("foo", "bar", 2000);

        System.out.println(cache.get("foo"));
        Thread.sleep(3000);
        System.out.println(cache.get("foo"));
    }
}
