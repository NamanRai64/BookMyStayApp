package com.hotel.app.service;

import com.hotel.app.exception.HotelAppException;
import com.hotel.app.model.Reservation;
import java.util.Optional;
import java.util.Stack;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Responsibility: Reverses state changes for cancelled bookings.
 * Key Concept: Stack Data Structure - Stores recently released room IDs for LIFO rollback.
 */
public class CancellationService {
    private final BookingHistory history;
    private final RoomInventory inventory;
    
    // Requirement: A Stack<String> is used to track recently released room IDs.
    // Stacks follow a LIFO order, which naturally models rollback behavior.
    private final Stack<String> releasedRoomIds;

    public CancellationService(BookingHistory history, RoomInventory inventory) {
        this.history = history;
        this.inventory = inventory;
        this.releasedRoomIds = new Stack<>();
    }

    /**
     * Requirement: Allow cancellation of confirmed bookings only.
     * Requirement: Validate reservation existence before performing rollback.
     * Requirement: Controlled Mutation - Rollback operations in strict order (Inventory -> Stack -> History).
     */
    public void cancelBooking(String roomId) throws HotelAppException {
        System.out.println(">> System: Processing Cancellation Request for Room " + roomId);
        
        // 1. Validation: Does the reservation exist?
        Optional<Reservation> reservationOpt = history.getHistoricalRecords().stream()
                .filter(res -> res.getRoomId().equals(roomId))
                .findFirst();

        if (reservationOpt.isEmpty()) {
            throw new HotelAppException("Cancellation Failed: Reservation ID '" + roomId + "' does not exist.");
        }

        Reservation res = reservationOpt.get();

        // 2. Prevent duplicate cancellation
        if (res.isCancelled()) {
            throw new HotelAppException("Rollback Interrupted: Reservation for Room '" + roomId + "' is already cancelled.");
        }

        // 3. Inventory Rollback
        // Requirement: Inventory count for the corresponding room type is incremented.
        inventory.updateAvailability(res.getRoomType(), 1);
        
        // 4. Record the released room ID in a rollback structure (Stack)
        // Requirement: LIFO order naturally reflects the undo behavior.
        releasedRoomIds.push(roomId);
        
        // 5. Update History
        res.setCancelled(true);
        
        System.out.println(">> System: SUCCESS. Inventory restored for '" + res.getRoomType() + "'.");
        System.out.println(">> System: Rollback Stack updated with Room " + roomId);
    }

    /**
     * Requirement: Allow retrieval of released room IDs.
     */
    public void displayRollbackState() {
        System.out.println("\n--- Administrative System: Rollback (Released Room Stack) ---");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No recently cancelled bookings.");
        } else {
            System.out.println("LIFO Order of Released IDs: " + releasedRoomIds);
            System.out.println("Total Reclaimed Inventory: " + releasedRoomIds.size());
        }
        System.out.println("----------------------------------------------------------\n");
    }

    public Stack<String> getReleasedRoomIds() {
        return releasedRoomIds;
    }
}
