package com.nainesh.creational.singleton;

public enum EnumSingleton {
    INSTANCE;

    public void doSomething() {
        System.out.println("Do something");
    }

    public static void main(String[] args) {
        EnumSingleton.INSTANCE.doSomething();
    }
}
