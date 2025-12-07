package com.nainesh.problems.tictactoe.strategy;

import com.nainesh.problems.tictactoe.models.Board;
import com.nainesh.problems.tictactoe.models.Player;

public class DiagonalWinningStrategy implements WinningStrategy{
    @Override
    public boolean checkWinner(Board board, Player player) {
        //MAIN DIAGONAL
        //00, 11, 22
        boolean mainDiagWin =true;
        for(int i=0;i< board.getSize();i++){
            if(board.getCell(i,i).getSymbol()!=player.getSymbol()){
                mainDiagWin = false;
                break;
            }
        }
        if(mainDiagWin) return true;

        //ANTI-DIAGONAL
        //02, 11, 20
        boolean antiDiagWin =true;
        for(int i=0;i< board.getSize();i++){
            if(board.getCell(i,board.getSize()-i-1).getSymbol()!=player.getSymbol()){
                antiDiagWin = false;
                break;
            }
        }
        return antiDiagWin;
    }
}
