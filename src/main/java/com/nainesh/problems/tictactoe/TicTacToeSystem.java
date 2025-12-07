package com.nainesh.problems.tictactoe;

import com.nainesh.problems.tictactoe.exceptions.InvalidMoveException;
import com.nainesh.problems.tictactoe.models.Player;
import com.nainesh.problems.tictactoe.observer.Scoreboard;

public class TicTacToeSystem {
    private static TicTacToeSystem instance = new TicTacToeSystem();
    private Game game;
    private final Scoreboard scoreboard;
    private TicTacToeSystem(){
        this.scoreboard= new Scoreboard();
    }

    public static TicTacToeSystem getInstance(){
        return instance;
    }

    public void createGame(Player p1, Player p2){
        this.game = new Game(p1, p2);
        this.game.addObserver(scoreboard);
        System.out.printf("Game started b/w %s (X) and %s(0)\n", p1.getName(), p2.getName());
    }

    public void makeMove(Player p, int r, int c){
        if(game==null){
            System.out.println("No game in progress. pLease create a game first");
            return;
        }

        try{
            System.out.printf("%s plays at (%d, %d) %n", p.getName(), r, c);
            game.makeMove(p, r, c);
            game.getBoard().printBoard();
            System.out.println("Game status:"+game.getStatus());
            if(game.getWinner()!=null){
                System.out.println("Winner : "+game.getWinner().getName());
            }
        }catch(InvalidMoveException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void printBoard(){
        game.getBoard().printBoard();
    }

    public void printScorecard(){
        scoreboard.printScores();
    }


    public void printScoreBoard() {
        game.getBoard().printBoard();
    }
}
