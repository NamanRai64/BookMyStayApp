package model;

public class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 400.0, 180.0);
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