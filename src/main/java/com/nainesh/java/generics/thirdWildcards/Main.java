package com.nainesh.java.generics.thirdWildcards;

import java.util.Arrays;
import java.util.List;

public class Main {


    public static <T> void printList(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
    /**
     * ? allows passing lists of any type.
     * Useful for read-only operations.
     * */
    public static void printList(List<?> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static void main(String[] args) {
        printList(Arrays.asList(1,2,3,4));
        printList(Arrays.asList("Albus", "Percival", "Wulfric" ,"Brian", "Dumbledore"));
    }
}
