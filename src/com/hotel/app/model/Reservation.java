package com.hotel.app.model;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a confirmed booking stored in history.
 * Key Concept: Historical Tracking - Once stored, form an audit trail.
 */
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String guestName;
    private final String roomType;
    private final String roomId;
    private final double basePrice;
    private final LocalDateTime confirmationTime;
    private final List<AddOnService> addOns;
    private boolean cancelled = false; // UC 10: Cancellation Support 

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
    public boolean isCancelled() { return cancelled; }
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

    public double getTotalCost() {
        double total = basePrice;
        for (AddOnService service : addOns) {
            total += service.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        String status = cancelled ? "[CANCELLED]" : "[CONFIRMED]";
        return String.format("%s Reservation[Guest: %s, Room: %s (%s), Total: $%.2f, Date: %s]", 
            status, guestName, roomId, roomType, getTotalCost(), confirmationTime);
    }
}
