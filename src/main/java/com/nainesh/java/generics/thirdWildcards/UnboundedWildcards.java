package com.nainesh.java.generics.thirdWildcards;

import java.util.List;

public class UnboundedWildcards {

    // Method accepts a List of ANY type
    public static void printList(List<?> list) {

        //Element type unknown, treat as Objects
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static void main(String[] args) {
        List<String> names = List.of("Alice", "Bob");
        List<Integer> numbers = List.of(1, 2, 3);

        // Both lists are accepted because of '?'
        printList(names);
        printList(numbers);
    }
}
