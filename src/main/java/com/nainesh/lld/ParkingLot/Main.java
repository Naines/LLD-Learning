package com.nainesh.lld.ParkingLot;

import com.nainesh.lld.ParkingLot.entities.*;
import com.nainesh.lld.ParkingLot.enums.Size;
import com.nainesh.lld.ParkingLot.strategy.HourlyFeeStrategy;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        ParkingLotSystem parkingLotSystem = ParkingLotSystem.getIns();
        //1.INIT FLOORS AND SPOTS
        //2. INIT VEHICLES
        //3. Simulate cases

        Floor floor1 = new Floor(1);
        floor1.addSpot(new ParkingSpot("F1-S1", Size.SMALL));
        floor1.addSpot(new ParkingSpot("F1-M1", Size.MEDIUM));
        floor1.addSpot(new ParkingSpot("F1-L1", Size.LARGE));

        Floor floor2 = new Floor(2);
        floor2.addSpot(new ParkingSpot("F2-M1", Size.MEDIUM));
        floor2.addSpot(new ParkingSpot("F2-M2", Size.MEDIUM));

        parkingLotSystem.addFloor(floor1);
        parkingLotSystem.addFloor(floor2);

        parkingLotSystem.setFeeStrategy(new HourlyFeeStrategy());

        Vehicle car = new Car("C-456");
        Vehicle truck = new Truck("T-789");
        Vehicle truck1 = new Truck("T-790");
        Vehicle car2 = new Car("C-457");
        Vehicle car3 = new Car("C-458");


        //4. Simulate vehicle entries
        System.out.println("\n--- Vehicle Entries ---");
        floor1.findAvailableSpots();
        floor2.findAvailableSpots();


        Optional<Ticket> carTicketOpt = parkingLotSystem.parkVehicle(car);
        Optional<Ticket> truckTicketOpt = parkingLotSystem.parkVehicle(truck);
        Optional<Ticket> truckTicketOpt1 = parkingLotSystem.parkVehicle(truck1);

        Optional<Ticket> carTicketOpt1 = parkingLotSystem.parkVehicle(car2);
        Optional<Ticket> carTicketOpt2 = parkingLotSystem.parkVehicle(car3);

        System.out.println("\n--- Availability after parking ---");
        floor1.findAvailableSpots();
        floor2.findAvailableSpots();

        // 5. Simulate vehicle exits and fee calculation
        System.out.println("\n--- Vehicle Exits ---");

        if (carTicketOpt.isPresent()) {
            Optional<Double> feeOpt = parkingLotSystem.unParkVehicle(car);
            feeOpt.ifPresent(fee -> System.out.printf("Car C-456 unparked. Fee: $%.2f\n", fee));
        }

        System.out.println("\n--- Availability after one car leaves ---");
        floor1.findAvailableSpots();
        floor2.findAvailableSpots();
    }
}
