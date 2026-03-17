package com.hotel.app.service;

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
     */
    public String allocateRoom(ReservationRequest request) {
        String type = request.getRoomType();
        
        // 1. Check availability
        if (inventory.getAvailability(type) > 0) {
            // 2. Decrement inventory immediately (Atomic Logical Operation)
            inventory.updateAvailability(type, -1);
            
            // 3. Generate Unique Room ID
            String roomId = type.substring(0, 1).toUpperCase() + (roomCounter++);
            
            // 4. Record to prevent reuse (Set Data Structure)
            allocatedRooms.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);
            
            return roomId;
        }
        
        return null; // No availability
    }

    public void displayAllocations() {
        System.out.println("\n--- Current Room Allocations ---");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " Rooms: " + entry.getValue());
        }
        System.out.println("--------------------------------\n");
    }
}
