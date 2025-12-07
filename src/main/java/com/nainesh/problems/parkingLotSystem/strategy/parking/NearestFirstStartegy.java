package com.nainesh.problems.parkingLotSystem.strategy.parking;


import com.nainesh.problems.parkingLotSystem.entities.ParkingFloor;
import com.nainesh.problems.parkingLotSystem.entities.ParkingSpot;
import com.nainesh.problems.parkingLotSystem.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

public class NearestFirstStartegy implements ParkingStrategy{
    @Override
    public Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            Optional<ParkingSpot> spot = floor.findAvailableSpot(vehicle);
            if (spot.isPresent()) {
                return spot;
            }
        }
        return Optional.empty();
    }
}
