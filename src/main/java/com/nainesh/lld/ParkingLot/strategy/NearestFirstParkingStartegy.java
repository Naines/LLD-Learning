package com.nainesh.lld.ParkingLot.strategy;

import com.nainesh.lld.ParkingLot.entities.Floor;
import com.nainesh.lld.ParkingLot.entities.ParkingSpot;
import com.nainesh.lld.ParkingLot.entities.Vehicle;

import java.util.List;
import java.util.Optional;

public class NearestFirstParkingStartegy implements ParkingStrategy{

    @Override
    public Optional<ParkingSpot> findSpot(List<Floor> floors, Vehicle vehicle) {
        for(Floor floor: floors){
            Optional<ParkingSpot> spot = floor.findAvailableSpot(vehicle);
            if(spot.isPresent()) return spot;
        }
        return Optional.empty();
    }
}
