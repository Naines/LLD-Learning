package com.nainesh.lld.HashMap;

import java.util.LinkedList;

class HashMap<K, V> {
    private class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int capacity = 16; // default size
    private LinkedList<Entry<K, V>>[] table;

    @SuppressWarnings("unchecked")
    public HashMap() {
        table = new LinkedList[capacity];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    public void put(K key, V value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value; // update existing key
                return;
            }
        }
        table[index].add(new Entry<>(key, value)); // insert new key-value pair
    }

    public V get(K key) {
        int index = hash(key);
        if (table[index] == null) return null;
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null; // not found
    }

    public boolean remove(K key) {
        int index = hash(key);
        if (table[index] == null) return false;
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                table[index].remove(entry);
                return true;
            }
        }
        return false;
    }
}

public class Main {
    public static void main(String[] args) {

        HashMap<String, Integer> map = new HashMap<>();

        map.put("apple", 10);
        map.put("banana", 20);

        System.out.println(map.get("apple"));   // Output: 10
        System.out.println(map.get("banana"));  // Output: 20

        map.remove("apple");
        System.out.println(map.get("apple"));   // Output: null
    }
}
