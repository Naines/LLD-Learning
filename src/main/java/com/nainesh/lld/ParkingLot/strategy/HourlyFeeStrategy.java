package com.nainesh.lld.ParkingLot.strategy;

import com.nainesh.lld.ParkingLot.entities.Ticket;

public class HourlyFeeStrategy implements FeeStrategy{

    double RATE_PER_HOUR = 10.0;
    @Override
    public double calculateFee(Ticket ticket) {
        long duration = ticket.getExitTime() - ticket.getEntryTime();
        long hours = (duration/(1000*60*60));
        return hours * RATE_PER_HOUR;
    }
}
