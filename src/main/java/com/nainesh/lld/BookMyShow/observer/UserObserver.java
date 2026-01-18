package com.nainesh.lld.BookMyShow.observer;

import com.nainesh.lld.BookMyShow.entities.Movie;
import com.nainesh.lld.BookMyShow.entities.User;

public class UserObserver implements MovieObserver{
    User user;
    public UserObserver(User user){
        this.user = user;
    }
    @Override
    public void update(Movie movie) {
        System.out.printf("Movie is available for booking"+movie.getTitle());
    }
}
