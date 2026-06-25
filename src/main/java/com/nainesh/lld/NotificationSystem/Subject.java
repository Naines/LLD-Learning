package com.nainesh.lld.NotificationSystem;

public interface Subject {

    void addObserver(Observer ob);
    void removeObserver(Observer ob);
    void notifyObservers();
}
