package com.nainesh.problems.tictactoe.state;

import com.nainesh.problems.tictactoe.Game;
import com.nainesh.problems.tictactoe.exceptions.InvalidMoveException;
import com.nainesh.problems.tictactoe.models.Player;

public class DrawState implements GameState{


    @Override
    public void handleMove(Game game, Player player, int r, int c) {
        throw new InvalidMoveException("Game is already over. It was a draw");
    }
}
