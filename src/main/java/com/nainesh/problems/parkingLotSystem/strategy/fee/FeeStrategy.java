package com.nainesh.problems.parkingLotSystem.strategy.fee;

import com.nainesh.problems.parkingLotSystem.entities.ParkingTicket;

public interface FeeStrategy {

    double calculateFee(ParkingTicket parkingTicket);
}
