package com.nainesh.lld.BookMyShow.entities;

import com.nainesh.lld.BookMyShow.Strategy.pricing.PricingStartegy;

import java.time.LocalDateTime;

public class Show {
    String id;
    Movie movie;
    Screen screen;
    LocalDateTime startTime;
    PricingStartegy pricingStartegy;

    public Show(String id, Movie movie, Screen screen, LocalDateTime startTime, PricingStartegy pricingStartegy) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.pricingStartegy = pricingStartegy;
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public PricingStartegy getPricingStartegy() {
        return pricingStartegy;
    }
}
