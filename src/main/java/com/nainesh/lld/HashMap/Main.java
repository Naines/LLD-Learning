package com.nainesh.lld.HashMap;

import java.util.LinkedList;

/**create array of linkedlist.
 * A HashMap uses an array of buckets.
 * Each bucket stores a linked list (or in modern Java,
 * sometimes a balanced tree for performance).
 * Keys are mapped to buckets using a hash function.
 *
 * hashfunction - Converts a key into an integer index within the array.
 * Example: index = hash(key) % capacity.
 *
 * collision handling:
 * Different keys may hash to the same index.
 * To handle this, each bucket is a linked list (or tree).
 * New entries are appended to the list if a collision occurs.
 *
 * Load Factor:
 * Ratio: size / capacity.
 * Default in Java is 0.75.
 * When exceeded, the HashMap rehashes (doubles capacity and redistributes entries).
 * Balances memory usage and lookup performance. When load factor is exceeded,
 * capacity is increased (usually doubled).
 * All existing entries are rehashed into the new array.Expensive operation, but infrequent.
 *
 * 0.75:
 * A lower load factor (e.g., 0.5) means the table resizes more often → fewer collisions but higher memory usage.
 * A higher load factor (e.g., 0.9) means fewer resizes → better memory efficiency but more collisions, slowing down lookups.
 * 0.75 was chosen empirically as the “sweet spot” where average performance remains close to O(1) while memory overhead is reasonable.
 */
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
