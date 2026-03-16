package model;

/**
 * Use Case 5: Booking Request (First-Come-First-Served)
 * Represents a guest's intent to book a room.
 * This is different from a confirmed Booking as it hasn't been allocated yet.
 */
public class ReservationRequest {
    private String guestName;
    private String roomType;

    public ReservationRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Request[Guest: " + guestName + ", Room: " + roomType + "]";
    }
}
