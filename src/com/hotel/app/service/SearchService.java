package com.hotel.app.service;

import com.hotel.app.model.Room;
import java.util.Map;
import java.util.Set;

public class SearchService {
    private RoomInventory inventory;
    private Map<String, Room> roomCatalog;

    public SearchService(RoomInventory inventory, Map<String, Room> roomCatalog) {
        this.inventory = inventory;
        this.roomCatalog = roomCatalog;
    }

    public void searchAvailableRooms() {
        System.out.println("\n--- Search Results: Available Rooms ---");
        Set<String> roomTypes = inventory.getAllRoomTypes();
        for (String type : roomTypes) {
            int availableCount = inventory.getAvailability(type);
            if (availableCount > 0) {
                Room roomDetails = roomCatalog.get(type);
                if (roomDetails != null) {
                    System.out.printf("%-15s | $%-14.2f | %-10d | %-10d\n", 
                        roomDetails.getRoomType(), roomDetails.getPrice(), 
                        roomDetails.getNumberOfBeds(), availableCount);
                }
            }
        }
        System.out.println("--------------------------------------------------\n");
    }

    public void displayRoomSummary(String roomType) {
        int available = inventory.getAvailability(roomType);
        Room room = roomCatalog.get(roomType);
        if (room != null) {
            System.out.println("Quick Summary for " + roomType + ": " + (available > 0 ? "AVAILABLE" : "SOLD OUT"));
        }
    }
}
