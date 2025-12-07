package com.nainesh.problems.parkingLotSystem.strategy.parking;

import com.nainesh.problems.parkingLotSystem.entities.ParkingFloor;
import com.nainesh.problems.parkingLotSystem.entities.ParkingSpot;
import com.nainesh.problems.parkingLotSystem.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

public interface ParkingStrategy {
    Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, Vehicle vehicle);
}
