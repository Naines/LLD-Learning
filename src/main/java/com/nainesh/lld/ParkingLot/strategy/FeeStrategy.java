package com.nainesh.lld.ParkingLot.strategy;

import com.nainesh.lld.ParkingLot.entities.Ticket;

public interface FeeStrategy {
    double calculateFee(Ticket ticket);
}
