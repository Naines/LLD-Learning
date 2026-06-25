package com.nainesh.designPatterns.Observer;

public interface Subject {

    void addObserver(Observer ob);
    void removeObserver(Observer ob);
    void notifyObservers();
}
