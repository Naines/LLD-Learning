package com.nainesh.problems.tictactoe.exceptions;

public class InvalidMoveException extends RuntimeException{
    public InvalidMoveException(String msg){
        super(msg);
    }
}
