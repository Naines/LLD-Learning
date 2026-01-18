package com.nainesh.lld.BookMyShow.Strategy.pricing;

import com.nainesh.lld.BookMyShow.entities.Seat;

import java.util.List;

public interface PricingStartegy {
    double calculatePrice(List<Seat> seats);
}
