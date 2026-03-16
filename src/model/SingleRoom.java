package model;

public class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 250.0, 100.0);
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