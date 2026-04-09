package com.nainesh.java.generics.FourUpperLowerBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * <? super Integer> means “some unknown supertype of Integer.”
 *
 * You can write safely (add Integers), but when reading,
 * elements are only guaranteed to be Object.
 * */
public class LowerBoundedWildcard {

    // Accepts a list of Integer or any superclass (Number, Object)
    // Safe: we can add Integers because the list is guaranteed
    // to accept Integer or its supertypes

    public static void addIntegers(List<? super Integer> list) {
        list.add(10);
        list.add(20);
    }

    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<>();
        List<Number> nums = new ArrayList<>();
        List<Object> objs = new ArrayList<>();

        addIntegers(ints);  // Works
        addIntegers(nums);  // Works
        addIntegers(objs);  // Works

        System.out.println(ints);
        System.out.println(nums);
        System.out.println(objs);
    }
}

/**
 * <?> → unknown type, read-only, safest when you don’t care about element type.
 * <? extends T> → upper bound, can read as T, but cannot safely add.
 * <? super T> → lower bound, can add T, but when reading you only get Object.
 *
 *
 * Use <? extends T> when you need to consume data (read).
 * Use <? super T> when you need to produce data (write).
 * Use <?> when you just need flexibility without caring about type.
 */
