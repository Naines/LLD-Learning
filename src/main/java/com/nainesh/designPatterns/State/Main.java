package com.nainesh.designPatterns.State;

/**
 * @author Nainesh
 * Context changes its state automatically on change on age.
 */


public class Main {
    public static void main(String[] args) {
        Subject adam = new Subject("A" +
                "dam");
//        for(int i=1;i<100;i++){
            adam.grow(35);
//        }
    }

}
