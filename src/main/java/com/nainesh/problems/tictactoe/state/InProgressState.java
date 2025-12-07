package com.nainesh.problems.tictactoe.state;

import com.nainesh.problems.tictactoe.Game;
import com.nainesh.problems.tictactoe.enums.GameStatus;
import com.nainesh.problems.tictactoe.enums.Symbol;
import com.nainesh.problems.tictactoe.exceptions.InvalidMoveException;
import com.nainesh.problems.tictactoe.models.Player;

public class InProgressState implements GameState{
    @Override
    public void handleMove(Game game, Player player, int r, int c) {
        if(game.getCurrPlayer()!=player)
            throw new InvalidMoveException("Not your turn!");

        game.getBoard().placeSymbol(r, c, player.getSymbol());

        //check for state change - winner, draw
        if(game.checkWinner(player)){
            game.setWinner(player);
            game.setStatus(player.getSymbol() == Symbol.X ? GameStatus.WINNER_X : GameStatus.WINNER_O);
            game.setState(new WinnerState()); //change state here
        }else if(game.getBoard().isFull()){
            game.setState(new DrawState());
            game.setStatus(GameStatus.DRAW);
        }else{
            //game is in progress, switch player
            game.switchPlayer();
        }
    }
}
