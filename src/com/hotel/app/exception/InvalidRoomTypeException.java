package com.hotel.app.exception;

/**
 * Thrown when an invalid room type is requested.
 */
public class InvalidRoomTypeException extends HotelAppException {
    public InvalidRoomTypeException(String roomType) {
        super("Invalid Error: Room Type '" + roomType + "' is not recognized by the system.");
    }
}
