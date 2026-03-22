package com.hotel.app.service;

import com.hotel.app.exception.NoAvailabilityException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RoomInventory {
    private final Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    public synchronized void registerRoomType(String roomType, int initialCount) {
        inventory.put(roomType, initialCount);
    }

    public synchronized int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public synchronized Set<String> getAllRoomTypes() {
        return new HashSet<>(inventory.keySet());
    }

    /**
     * Requirement: Critical Section protects shared inventory state.
     * Prevents interleaving operations that could lead to double allocation.
     */
    public synchronized void updateAvailability(String roomType, int change) throws NoAvailabilityException {
        if (!inventory.containsKey(roomType)) return;
        int currentCount = inventory.get(roomType);
        int newCount = currentCount + change;
        if (newCount < 0) {
            throw new NoAvailabilityException(roomType);
        }
        inventory.put(roomType, newCount);
    }

    /**
     * Requirement: Persistent Snapshot of current availability.
     */
    public synchronized Map<String, Integer> getInventorySnapshot() {
        return new HashMap<>(inventory);
    }

    /**
     * Requirement: Restore persisted data during application startup.
     */
    public synchronized void restoreFromSnapshot(Map<String, Integer> snapshot) {
        if (snapshot == null) return;
        inventory.clear();
        inventory.putAll(snapshot);
        System.out.println(">> System: Room Inventory restored from persistence snapshot.");
    }

    public void displayInventory() {
        System.out.println("\n--- Centralized Room Inventory Status ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.printf("%-15s | %-12d\n", entry.getKey(), entry.getValue());
        }
        System.out.println("----------------------------------------\n");
    }
}
