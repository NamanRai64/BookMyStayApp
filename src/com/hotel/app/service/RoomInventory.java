package com.hotel.app.service;

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

    public boolean updateAvailability(String roomType, int change) {
        if (!inventory.containsKey(roomType)) return false;
        int currentCount = inventory.get(roomType);
        int newCount = currentCount + change;
        if (newCount < 0) return false;
        inventory.put(roomType, newCount);
        return true;
    }

    public void displayInventory() {
        System.out.println("\n--- Centralized Room Inventory Status ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.printf("%-15s | %-12d\n", entry.getKey(), entry.getValue());
        }
        System.out.println("----------------------------------------\n");
    }
}
