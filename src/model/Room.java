package model;

/**
 * Key Concept: Abstract Class - Provides a template for specific room types.
 * Helps in enforcing a consistent structure for all rooms.
 */
public abstract class Room {

    protected String roomType;
    protected int numberOfBeds;
    protected double size;
    protected double price;

    public Room(String roomType, int numberOfBeds, double size, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public abstract void displayRoomDetails();

    // Getters
    public String getRoomType() { return roomType; }
    public int getNumberOfBeds() { return numberOfBeds; }
    public double getSize() { return size; }
    public double getPrice() { return price; }
}
