package com.nainesh.lld.HashMap;

import java.util.LinkedList;

/**
 * @author Nainesh
 * create array of linkedlist.
 * A HashMap uses an array of buckets.
 * Each bucket stores a linked list (or in modern Java,
 * sometimes a balanced tree for performance, after JAVA 8).
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

 Functions():-
 1. getHashKey()
 2. put(K key, V value)
 3. get(K key)
 4. remove(K key)

 5. rehash() -



 */
class HashMap<K, V> {

    int capacity;
    double loadFactor;
    public HashMap(int size, double loadfactor) {
        this.capacity = size;
        this.loadFactor = loadfactor;
        table = new LinkedList[capacity];
    }

    private class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int maxCapacity = 1 << 30;

    private int countNodes = 0;
    private LinkedList<Entry<K, V>>[] table;

//    public HashMap() {
//
//    }

    //get hashCode() from Object superclass and mod by size to get bucket.
    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    //1. put(k, v) -> find hashkey and put to the bucket.If occupied, transverse table and put at end.
    //2. if k, present already while transversing, update k.
    //3. On each add, countNodes++. If countNodes> loadFactor*capacity, rehash().
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
        countNodes++;
        table[index].add(new Entry<>(key, value));
        if(countNodes > loadFactor*table.length){
            rehash(table.length*2);
        }
    }

    //1. get hashkey
    //2. if table[hashkey] dont exist, return null, Else,
    //3. Transverse and find, if not found return null.
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

    //1. get key.
    //2. if(table[key]==null) nothing to remove. return false
    //3. else tranverse, find and remove list node and return true.
    //4. else return false;
    public boolean remove(K key) {
        int index = hash(key);
        if (table[index] == null) return false;
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                table[index].remove(entry);
                countNodes--;
                return true;
            }
        }
        return false;
    }

    //1. Create a new table and copy contents of old to new
    //2. Iterate the old table, one bucket to another, for each key, rehash
    // and put the k,v to the new table. Use existing put.
    void rehash(int newLength){
        if(newLength>maxCapacity){
            System.out.println("Hashmap is exceeding max capacity");
            return;
        }

        System.out.println("increasing size");

        LinkedList<Entry<K, V>> newTable[] = new LinkedList[newLength];
        for(LinkedList<Entry<K, V>> bucket: table){
            if(bucket==null) continue;
            for(Entry<K, V> e: bucket){
                int newIndex = Math.abs(e.key.hashCode()%newLength);
                if (newTable[newIndex] == null) {
                    newTable[newIndex] = new LinkedList<>();
                }
                newTable[newIndex].add(new Entry<>(e.key, e.value));
            }
        }

        this.table = newTable;
        this.capacity = newLength;
    }

    int get(K key, int n){
        return Math.abs(key.hashCode()%n);
    }
}

public class Main {
    public static void main(String[] args) {

        int size =4;
        double loadfactor=0.75;
        HashMap<String, Integer> map = new HashMap<>(size, loadfactor);

        map.put("apple", 10);
        map.put("banana", 20);
        map.put("10", 100);
        map.put("11", 101);
        map.put("12", 102);
        map.put("13", 103);

        System.out.println(map.get("apple"));   // Output: 10
        System.out.println(map.get("banana"));  // Output: 20
        System.out.println(map.get("10"));
        map.remove("apple");
        System.out.println(map.get("apple"));// Output: null
        System.out.println(map.get("10"));
        System.out.println(map.get("13"));
//        map.get("12");
    }
}
