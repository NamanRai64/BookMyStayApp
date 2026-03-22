package com.hotel.app.exception;

/**
 * Thrown when no rooms are available for a requested type.
 */
public class NoAvailabilityException extends HotelAppException {
    public NoAvailabilityException(String roomType) {
        super("System Constraint: Request Failed. No '" + roomType + "' rooms are available for allocation.");
    }
}
