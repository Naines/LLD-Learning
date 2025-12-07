package com.nainesh.problems.tictactoe.state;

import com.nainesh.problems.tictactoe.Game;
import com.nainesh.problems.tictactoe.models.Player;

public interface GameState{
    void handleMove(Game game, Player player, int r, int c);
}
