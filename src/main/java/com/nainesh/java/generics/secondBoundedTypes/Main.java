package com.nainesh.java.generics.secondBoundedTypes;

/**  Restricts generics to certain types (e.g., Number).
 * T extends NUmber -> T must be a subtype of Number
 *
 *
 * Bound it to anything of NUmber type. Hence addition is possible.*/
class Calculator<T extends Number> {
    public double add(T a, T b) {
        return a.doubleValue() + b.doubleValue();
    }
}


public class Main {
    public static void main(String[] args) {
        Calculator<Integer> intCalc = new Calculator<>();
        System.out.println(intCalc.add(10, 20));

        Calculator<Double> doubleCalc = new Calculator<>();
        System.out.println(doubleCalc.add(5.5, 4.5));
    }
}
