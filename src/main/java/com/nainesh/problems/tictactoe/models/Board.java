package com.nainesh.problems.tictactoe.models;

import com.nainesh.problems.tictactoe.enums.Symbol;
import com.nainesh.problems.tictactoe.exceptions.InvalidMoveException;

public class Board {

    private final int size;
    private int movesCount;
    private final Cell board[][];


    //default board
    public Board(int size) {
        this.size = size;
        this.board = new Cell[size][size];
        movesCount = 0;
        initBoard();
    }


    private void initBoard(){
        for(int r=0;r<size;r++){
            for(int c=0 ;c<size;c++){
                board[r][c] = new Cell();
            }
        }
    }

    /** mark symbol on board*/
    public boolean placeSymbol(int r, int c, Symbol symbol){
        if(r<0 || c<0 || r>=size ||c>=size)
            throw new InvalidMoveException("Invalid position out of bounds");
        if(board[r][c].getSymbol() != Symbol.EMPTY)
            throw new InvalidMoveException("Invalid position: cell is already occupied");
        board[r][c].setSymbol(symbol);
        movesCount++;
        return true;
    }

    public Cell getCell(int r, int c){
        if(r<0 || c<0 || r>=size ||c>=size)
            return null;
        return board[r][c];
    }

    public boolean isFull(){
        return movesCount == size*size;
    }

    public void printBoard(){
        System.out.println("----------");
        for(int i=0;i<size;i++){
            System.out.print("| ");
            for(int j=0;j<size;j++) {
                System.out.print(board[i][j].getSymbol().getChar() + " | ");
            }
            System.out.println("\n------------");
        }
    }

    public int getSize() {
        return size;
    }
}
