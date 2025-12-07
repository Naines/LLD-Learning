package com.nainesh.problems.parkingLotSystem;

import com.nainesh.problems.parkingLotSystem.entities.ParkingFloor;
import com.nainesh.problems.parkingLotSystem.entities.ParkingSpot;
import com.nainesh.problems.parkingLotSystem.entities.ParkingTicket;
import com.nainesh.problems.parkingLotSystem.strategy.fee.FeeStrategy;
import com.nainesh.problems.parkingLotSystem.strategy.fee.FlatRateFeeStrategy;
import com.nainesh.problems.parkingLotSystem.strategy.parking.BestFitStrategy;
import com.nainesh.problems.parkingLotSystem.strategy.parking.ParkingStrategy;
import com.nainesh.problems.parkingLotSystem.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {

    private static ParkingLot instance;
    private final List<ParkingFloor> floors = new ArrayList<>();
    private final Map<String, ParkingTicket> activeTickets;
    private FeeStrategy feeStrategy;
    private ParkingStrategy parkingStrategy;

    private ParkingLot(){
        this.feeStrategy = new FlatRateFeeStrategy();
        this.parkingStrategy = new BestFitStrategy();
        this.activeTickets = new ConcurrentHashMap<>();
    }

    public static synchronized ParkingLot getInstance() {
        if(instance==null){
            instance = new ParkingLot();
        }
        return instance;
    }

    public void addFloor(ParkingFloor parkingFloor){
        floors.add(parkingFloor);
    }

    public void setFeeStrategy(FeeStrategy feeStrategy){
        this.feeStrategy = feeStrategy;
    }

    public void setParkingStrategy(ParkingStrategy parkingStrategy){
        this.parkingStrategy = parkingStrategy;
    }

    //park Vehicle(Vehicle)
    //unparkVehicle(Vehicle)

    public Optional<ParkingTicket> parkVehicle(Vehicle vehicle){
        Optional<ParkingSpot> availableSpot = parkingStrategy.findSpot(floors, vehicle);

        if(availableSpot.isPresent()){
            ParkingSpot spot = availableSpot.get();
            spot.parkVehicle(vehicle);
            ParkingTicket parkingTicket = new ParkingTicket(vehicle, spot);
            activeTickets.put(vehicle.getLicenseNumber(), parkingTicket);
            System.out.printf("%s parked at %s. Ticket %s \n", vehicle.getLicenseNumber(), spot.getSpotId(), parkingTicket.getTicketId());
            return Optional.of(parkingTicket);
        }
        System.out.println("No available spot for " +vehicle.getLicenseNumber());
        return Optional.empty();
    }


    public Optional<Double> unparkVehicle(String licenseNumber) {
        ParkingTicket ticket = activeTickets.remove(licenseNumber);
        if (ticket == null) {
            System.out.println("Ticket not found");
            return Optional.empty();
        }

        ticket.setExitTime();
        ticket.getSpot().unParkVehicle();

        Double parkingFee = feeStrategy.calculateFee(ticket);
        return Optional.of(parkingFee);
    }


}
