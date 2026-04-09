package com.nainesh.java.generics.FourUpperLowerBounds;

import java.util.List;

/**
 * <? extends Number> means “some unknown subtype of Number.”
 * You can read safely as Number, but you cannot add new elements
 * (except null) because the exact subtype is unknown.
 *
 * */
public class UpperBoundedWildcard {

    // Accepts a list of Number or any subclass (Integer, Double, etc.)
    // Safe: we know every element is a Numbers
    public static double sumOfList(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        return sum;
    }

    public static void main(String[] args) {
        List<Integer> ints = List.of(1, 2, 3);
        List<Double> doubles = List.of(1.5, 2.5, 3.5);

        System.out.println(sumOfList(ints));    // Works with Integer
        System.out.println(sumOfList(doubles)); // Works with Double
    }
}
