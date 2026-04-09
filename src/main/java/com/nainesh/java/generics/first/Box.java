package com.nainesh.java.generics.first;

public class Box<T>{
    private T value;
    public void setValue(T value){
        this.value=value;
    }
    public T getValue(){
        return value;
    }
}

class Main{

    //the <T> after static is not about the return type. It’s a type parameter declaration for the method itself.
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }

    public static void main(String[] args) {
        //example 1 : one class work for multiple types
        Box<String> box=new Box<>();
        box.setValue("Nainesh's box");
        System.out.println(box.getValue());

        Box<Integer> intBox = new Box<>();
        intBox.setValue(42);
        System.out.println(intBox.getValue());

        //example 2 : methods operate on any type
        String[] names = {"A", "B", "C"};
        Integer[] numbers = {1, 2, 3};
        printArray(names);
        printArray(numbers);
    }
}
