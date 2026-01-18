package com.nainesh.lld.ParkingLot.strategy;

import com.nainesh.lld.ParkingLot.entities.Floor;
import com.nainesh.lld.ParkingLot.entities.ParkingSpot;
import com.nainesh.lld.ParkingLot.entities.Vehicle;

import java.util.List;
import java.util.Optional;

public interface ParkingStrategy {
    Optional<ParkingSpot> findSpot(List<Floor> floors, Vehicle vehicle);
}
