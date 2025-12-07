package com.nainesh.problems.tictactoe;

import com.nainesh.problems.tictactoe.enums.GameStatus;
import com.nainesh.problems.tictactoe.models.Board;
import com.nainesh.problems.tictactoe.models.Player;
import com.nainesh.problems.tictactoe.observer.GameSubject;
import com.nainesh.problems.tictactoe.state.GameState;
import com.nainesh.problems.tictactoe.state.InProgressState;
import com.nainesh.problems.tictactoe.strategy.ColumnWinningStrategy;
import com.nainesh.problems.tictactoe.strategy.DiagonalWinningStrategy;
import com.nainesh.problems.tictactoe.strategy.RowWinningStartegy;
import com.nainesh.problems.tictactoe.strategy.WinningStrategy;

import java.util.List;

public class Game extends GameSubject {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player currPlayer;
    private Player winner;
    private GameStatus status;
    private GameState state;
    private final List<WinningStrategy> winningStrategies;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.board = new Board(3);
        this.currPlayer = player1; //player 1 starts
        this.status = GameStatus.IN_PROGRESS;
        this.state = new InProgressState();
        this.winningStrategies = List.of(new ColumnWinningStrategy(), new RowWinningStartegy(), new DiagonalWinningStrategy());
    }



    public void makeMove(Player player, int r, int c){
        state.handleMove(this, player, r, c);
    }


    public boolean checkWinner(Player player){
        for(WinningStrategy strategy: winningStrategies){
            if(strategy.checkWinner(board, player)){
                return true;
            }
        }
        return false;
    }

    public void switchPlayer() {
        this.currPlayer = (currPlayer==this.player1)? player2: player1;
    }
    /// ///////////////////////////////////////////////////
    //getters and setters
    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public GameStatus getStatus() {
        return status;
    }

    public GameState getState() {
        return state;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
        //notify observers when game(Subject) status changes, i.e scoreboard
        if(status!=GameStatus.IN_PROGRESS){
            notifyObservers();
        }
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }


}
