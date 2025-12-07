package com.nainesh.problems.tictactoe.models;

import com.nainesh.problems.tictactoe.enums.Symbol;

public class Cell {
    private Symbol symbol;

    //default value of a cell
    public Cell(){
        this.symbol = Symbol.EMPTY;
    }

    public Symbol getSymbol(){
        return symbol;
    }
    public void setSymbol(Symbol symbol){
        this.symbol = symbol;
    }


}
