package com.nainesh.problems.tictactoe.observer;

import com.nainesh.problems.tictactoe.Game;

import java.util.ArrayList;
import java.util.List;


//here no Subject interface was used, we used abstract class
//Game would be subject
public abstract class GameSubject {
    private final List<GameObserver> observers = new ArrayList<>();
    public void addObserver(GameObserver observer){
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for(GameObserver observer: observers){
            observer.update((Game)this); //TypeCase to child class as we need child data in observer
        }
    }
}
