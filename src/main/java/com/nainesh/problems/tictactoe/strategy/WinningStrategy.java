package com.nainesh.problems.tictactoe.strategy;

import com.nainesh.problems.tictactoe.models.Board;
import com.nainesh.problems.tictactoe.models.Player;

public interface WinningStrategy {
    boolean checkWinner(Board board, Player player);
}
