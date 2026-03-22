package com.hotel.app.service;

import com.hotel.app.exception.NoAvailabilityException;
import com.hotel.app.model.ReservationRequest;
import java.util.*;

/**
 * Use Case 6: Booking Request Confirmation
 * Handles unique room allocation and prevents double-booking.
 */
public class AllocationService {
    private RoomInventory inventory;
    // Map of Room Type -> Set of Allocated Room IDs (Requirement: Uniqueness Enrollment)
    private Map<String, Set<String>> allocatedRooms;
    private int roomCounter = 101; // Start room numbers from 101

    public AllocationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
    }

    /**
     * Requirement: Generate and assign a unique room ID.
     * Prevents reuse across all allocations using Sets.
     * Requirement: Throws and handle custom exceptions for invalid scenarios.
     */
    public String allocateRoom(ReservationRequest request) throws NoAvailabilityException {
        String type = request.getRoomType();
        
        // 1. Decrement inventory immediately (Atomic Logical Operation)
        // Guarding System State: updateAvailability now throws NoAvailabilityException
        inventory.updateAvailability(type, -1);
        
        // 2. Generate Unique Room ID
        String roomId = type.substring(0, 1).toUpperCase() + (roomCounter++);
        
        // 3. Record to prevent reuse (Set Data Structure)
        allocatedRooms.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);
        
        return roomId;
    }

    public void displayAllocations() {
        System.out.println("\n--- Current Room Allocations ---");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " Rooms: " + entry.getValue());
        }
        System.out.println("--------------------------------\n");
    }
}
