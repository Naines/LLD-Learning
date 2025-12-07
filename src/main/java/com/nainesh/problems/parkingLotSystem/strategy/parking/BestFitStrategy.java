package com.nainesh.problems.parkingLotSystem.strategy.parking;

import com.nainesh.problems.parkingLotSystem.entities.ParkingFloor;
import com.nainesh.problems.parkingLotSystem.entities.ParkingSpot;
import com.nainesh.problems.parkingLotSystem.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

public class BestFitStrategy implements ParkingStrategy{
    @Override
    public Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, Vehicle vehicle) {
        Optional<ParkingSpot> bestSpot = Optional.empty();

        for (ParkingFloor floor : floors) {

            //return first available slot from Map
            Optional<ParkingSpot> spotOnThisFloor = floor.findAvailableSpot(vehicle);

            if (spotOnThisFloor.isPresent()) {
                if (bestSpot.isEmpty()) {
                    // If this is the first spot we've found, it's the best one so far.
                    bestSpot = spotOnThisFloor;
                } else {
                    // A smaller spot size enum ordinal means a tighter fit.
                    if (spotOnThisFloor.get().getSize().ordinal() < bestSpot.get().getSize().ordinal()) {
                        bestSpot = spotOnThisFloor;
                    }
                }
            }
        }
        return bestSpot;
    }
}
