package com.nainesh.java.generics.genericArrayList;


//used bounded generics
//add -> double the length if exceeded
//get -> simple
//remove -> remove the index and shift all ele to one left starting from idx.
class MyArrayList<T> {
    private Object[] data;   // backing array
    private int size;        // number of elements stored
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public void add(T element) {
        ensureCapacity();
        data[size++] = element;
    }

    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    //remove element at index
    //Index check if within limits
    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1]; // shift elements left
        }
        data[--size] = null; // clear last element
    }

    // Current size
    public int size() {
        return size;
    }

    // Ensure capacity (double when full)
    private void ensureCapacity() {
        if (size == data.length) {
            //create new array double the length and copy all
            Object[] newData = new Object[data.length * 2];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
    }

    // Index checx if within limits
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
public class Main {
    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("Alice");
        list.add("Bob");
        list.add("Charlie");

        System.out.println("Size: " + list.size());       // 3
        System.out.println("First element: " + list.get(0)); // Alice

        list.remove(1);
        System.out.println("After removal, size: " + list.size()); // 2
        System.out.println("Element at index 1: " + list.get(1));  // Charlie
    }
}

