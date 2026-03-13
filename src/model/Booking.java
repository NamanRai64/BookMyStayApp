import java.util.Date;

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
        room.setAvailable(false);
    }

    public void printBookingDetails() {
        System.out.println("\n--- BOOKING CONFIRMED ---");
        System.out.println("Customer: " + customerName);
        System.out.println("Room Type: " + room.getClass().getSimpleName());
        System.out.println("Check-In: " + checkIn);
        System.out.println("Check-Out: " + checkOut);
        System.out.println("Price/Night: " + room.getPricePerNight());
    }
}