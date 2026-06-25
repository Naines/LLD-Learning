package com.nainesh.designPatterns.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nainesh
 */
public class YoutubeChannel implements Subject {

    List<Observer> observers;
    String msg; //can be from a msg queue or from a cdc trigger

    YoutubeChannel(){
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer ob) {
        this.observers.add(ob);
    }

    @Override
    public void removeObserver(Observer ob) {
        this.observers.remove(ob);
    }

    @Override
    public void notifyObservers() {
        for(Observer ob: observers){
            ob.update(this.msg);
        }
    }

    //sets message
    public void doAction(String someWorkTitle){
        this.msg = someWorkTitle;
        System.out.println("Channel's work: "+this.msg);
        notifyObservers();
    }
}
