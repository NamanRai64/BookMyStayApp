package com.hotel.app.service;

import com.hotel.app.exception.NoAvailabilityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    public void registerRoomType(String roomType, int initialCount) {
        inventory.put(roomType, initialCount);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Set<String> getAllRoomTypes() {
        return inventory.keySet();
    }

    /**
     * Requirement: Prevent inventory from reaching invalid or negative values.
     * Throws an exception if change would result in negative counts (Guarding System State).
     */
    public void updateAvailability(String roomType, int change) throws NoAvailabilityException {
        if (!inventory.containsKey(roomType)) return;
        int currentCount = inventory.get(roomType);
        int newCount = currentCount + change;
        if (newCount < 0) {
            throw new NoAvailabilityException(roomType);
        }
        inventory.put(roomType, newCount);
    }

    public void displayInventory() {
        System.out.println("\n--- Centralized Room Inventory Status ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.printf("%-15s | %-12d\n", entry.getKey(), entry.getValue());
        }
        System.out.println("----------------------------------------\n");
    }
}
