package com.hotel.app.model;

public class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 750.0, 350.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds:      " + numberOfBeds);
        System.out.println("Size:      " + size + " sq. ft.");
        System.out.println("Price:     $" + price);
        System.out.println("---------------------------");
    }
}
