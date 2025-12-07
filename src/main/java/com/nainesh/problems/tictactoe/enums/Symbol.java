package com.nainesh.problems.tictactoe.enums;

public enum Symbol {
    X('X'), O('O'), EMPTY(' ');

    private final char symbol;
    Symbol(char symbol) {
        this.symbol = symbol;
    }
    public char getChar(){
        return symbol;
    }
}
