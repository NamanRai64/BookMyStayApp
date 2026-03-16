package model;

import java.util.Date;

/**
 * Represent a booking transaction.
 * Fixed: Added package declaration and ensured compatibility with Room class.
 */
public class Booking {
    private String customerName;
    private Room room;
    private Date checkIn;
    private Date checkOut;

    public Booking(String customerName, Room room, Date checkIn, Date checkOut) {
        this.customerName = customerName;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public void printBookingDetails() {
        System.out.println("Booking for: " + customerName);
        System.out.println("Room Type:   " + room.getRoomType());
        System.out.println("Check-in:    " + checkIn);
        System.out.println("Check-out:   " + checkOut);
        System.out.println("Price/Night: $" + room.getPrice());
    }
}