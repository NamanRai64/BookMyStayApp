package com.hotel.app.service;

import com.hotel.app.exception.*;
import com.hotel.app.model.ReservationRequest;

/**
 * Use Case 9: Error Handling & Validation
 * Responsibility: Validates inputs and system constraints before critical operations.
 * Key Concept: Fail-Fast Design - Detect and stop invalid requests early.
 */
public class BookingValidator {
    private final RoomInventory inventory;

    public BookingValidator(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Requirement: Validate room types before processing bookings.
     * Requirement: Display clear and informative failure messages via exceptions.
     */
    public void validateRequest(ReservationRequest request) throws InvalidRoomTypeException, NoAvailabilityException {
        String type = request.getRoomType();
        
        // 1. Input Validation - Does the type exist?
        if (!inventory.getAllRoomTypes().contains(type)) {
            throw new InvalidRoomTypeException(type);
        }
        
        // 2. State Validation - Is there any left?
        if (inventory.getAvailability(type) <= 0) {
            throw new NoAvailabilityException(type);
        }
        
        System.out.println(">> Validation Success: Request for '" + type + "' is valid and available.");
    }
}
