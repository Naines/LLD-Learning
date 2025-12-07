package com.nainesh.problems.tictactoe.strategy;

import com.nainesh.problems.tictactoe.models.Board;
import com.nainesh.problems.tictactoe.models.Player;

public class ColumnWinningStrategy implements WinningStrategy {
    @Override
    public boolean checkWinner(Board board, Player player) {
        //transverse all col to check if player's symbol is same
        for (int col = 0; col < board.getSize(); col++) {
            boolean colWin =true;
            for (int row = 0; row < board.getSize(); row++) {
                if(board.getCell(row, col).getSymbol() != player.getSymbol()){
                    colWin=false;
                    break;
                }
            }
            if(colWin) return true;
        }
        return false;
    }
}
