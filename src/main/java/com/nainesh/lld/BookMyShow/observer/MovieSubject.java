package com.nainesh.lld.BookMyShow.observer;

import com.nainesh.lld.BookMyShow.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public abstract class MovieSubject {
    List<MovieObserver> observers = new ArrayList<>();
    public void addObserver(MovieObserver observer){
        observers.add(observer);
    }

    public void removeObserver(MovieObserver observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for(MovieObserver observer: observers){
            observer.update((Movie)this);
        }
    }
}
