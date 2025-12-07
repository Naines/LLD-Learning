package com.nainesh.problems.parkingLotSystem.strategy.fee;


import com.nainesh.problems.parkingLotSystem.entities.ParkingTicket;
import com.nainesh.problems.parkingLotSystem.vehicle.VehicleSize;

import java.util.Map;

public class VehicleBasedFeeStartegy implements FeeStrategy{

    private static final Map<VehicleSize, Double> HOURLY_RATES = Map.of(
            VehicleSize.SMALL, 10.0,
            VehicleSize.MEDIUM, 20.0,
            VehicleSize.LARGE, 30.0
    );

    @Override
    public double calculateFee(ParkingTicket parkingTicket) {
        long duration = parkingTicket.getExitTime() - parkingTicket.getEntryTime();
        long hours = (duration / (1000 * 60 * 60)) + 1;
        return hours * HOURLY_RATES.get(parkingTicket.getVehicle().getSize());
    }
}
