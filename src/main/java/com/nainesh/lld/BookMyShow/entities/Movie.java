package com.nainesh.lld.BookMyShow.entities;

import com.nainesh.lld.BookMyShow.observer.MovieSubject;

public class Movie extends MovieSubject {
    String id;
    String description;
    String title;
    int durationInSeconds;

    public Movie(String id, String title, int durationInSeconds) {
        this.id = id;
        this.description = title;
        this.title = title;
        this.durationInSeconds = durationInSeconds;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
