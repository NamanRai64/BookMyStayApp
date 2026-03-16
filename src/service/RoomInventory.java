package service;

import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 3: Centralized Room Inventory Management
 * This class acts as the "Single Source of Truth" for room availability.
 */
public class RoomInventory {
    // Key Concept: HashMap - Maps Room Type (String) to Available Count (Integer)
    private Map<String, Integer> inventory;

    /**
     * Requirement: Initialize room availability using a constructor.
     */
    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    /**
     * Requirement: Register room types with their available counts.
     */
    public void registerRoomType(String roomType, int initialCount) {
        inventory.put(roomType, initialCount);
    }

    /**
     * Requirement: Provide methods to retrieve current availability.
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Requirement: Support controlled updates to room availability.
     * @param roomType The type of room to update.
     * @param change Positive to increase (cancellation), negative to decrease (booking).
     * @return true if update was successful, false if insufficient inventory.
     */
    public boolean updateAvailability(String roomType, int change) {
        if (!inventory.containsKey(roomType)) {
            return false;
        }

        int currentCount = inventory.get(roomType);
        int newCount = currentCount + change;

        if (newCount < 0) {
            // Prevention of double-booking/overbooking foundation
            return false;
        }

        inventory.put(roomType, newCount);
        return true;
    }

    /**
     * Requirement: Display inventory state.
     */
    public void displayInventory() {
        System.out.println("\n--- Centralized Room Inventory Status ---");
        System.out.printf("%-15s | %-12s\n", "Room Type", "Available");
        System.out.println("----------------------------------------");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.printf("%-15s | %-12d\n", entry.getKey(), entry.getValue());
        }
        System.out.println("----------------------------------------\n");
    }
}
