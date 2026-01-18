package com.nainesh.lld.BookMyShow.Strategy.pricing;

import com.nainesh.lld.BookMyShow.entities.Seat;

import java.util.List;

public class WeekdayPricingStartegy implements PricingStartegy{

    @Override
    public double calculatePrice(List<Seat> seats) {
        return seats.stream().mapToDouble(seat-> seat.getType().getPrice()).sum();
    }
}
