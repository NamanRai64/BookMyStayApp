package com.hotel.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a confirmed booking stored in history.
 * Key Concept: Historical Tracking - Once stored, form an audit trail.
 */
public class Reservation {
    private final String guestName;
    private final String roomType;
    private final String roomId;
    private final double basePrice;
    private final LocalDateTime confirmationTime;
    private final List<AddOnService> addOns;

    public Reservation(String guestName, String roomType, String roomId, double basePrice) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.basePrice = basePrice;
        this.confirmationTime = LocalDateTime.now();
        this.addOns = new ArrayList<>();
    }

    public void addAddOn(AddOnService service) {
        this.addOns.add(service);
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
    public double getBasePrice() { return basePrice; }
    public LocalDateTime getConfirmationTime() { return confirmationTime; }
    public List<AddOnService> getAddOns() { return new ArrayList<>(addOns); }

    public double getTotalCost() {
        double total = basePrice;
        for (AddOnService service : addOns) {
            total += service.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return String.format("Reservation[Guest: %s, Room: %s (%s), Total: $%.2f, Date: %s]", 
            guestName, roomId, roomType, getTotalCost(), confirmationTime);
    }
}
