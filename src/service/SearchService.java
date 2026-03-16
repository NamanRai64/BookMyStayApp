package service;

import model.Room;
import java.util.Map;
import java.util.Set;

/**
 * Use Case 4: Room Search & Availability Check
 * Responsible for read-only access to inventory and room information.
 */
public class SearchService {
    private RoomInventory inventory;
    private Map<String, Room> roomCatalog;

    /**
     * Requirement: Initialize with inventory and room catalog.
     */
    public SearchService(RoomInventory inventory, Map<String, Room> roomCatalog) {
        this.inventory = inventory;
        this.roomCatalog = roomCatalog;
    }

    /**
     * Requirement: Display only room types with availability greater than zero.
     * Uses Defensive Programming to ensure read-only access.
     */
    public void searchAvailableRooms() {
        System.out.println("\n--- Search Results: Available Rooms ---");
        System.out.printf("%-15s | %-15s | %-10s | %-10s\n", "Room Type", "Price/Night", "Beds", "Available");
        System.out.println("------------------------------------------------------------------");

        Set<String> roomTypes = inventory.getAllRoomTypes();
        boolean found = false;

        for (String type : roomTypes) {
            int availableCount = inventory.getAvailability(type);
            
            // Validation Logic: Exclude rooms with zero availability
            if (availableCount > 0) {
                Room roomDetails = roomCatalog.get(type);
                if (roomDetails != null) {
                    System.out.printf("%-15s | $%-14.2f | %-10d | %-10d\n", 
                        roomDetails.getRoomType(), 
                        roomDetails.getPrice(), 
                        roomDetails.getNumberOfBeds(),
                        availableCount);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available matching your criteria.");
        }
        System.out.println("------------------------------------------------------------------\n");
    }

    /**
     * Requirement: Ensure inventory data is not modified during search operations.
     * This method provides a summary without any state mutation.
     */
    public void displayRoomSummary(String roomType) {
        int available = inventory.getAvailability(roomType);
        Room room = roomCatalog.get(roomType);

        if (room != null) {
            System.out.println("Quick Summary for " + roomType + ":");
            System.out.println("Status: " + (available > 0 ? "AVAILABLE (" + available + ")" : "SOLD OUT"));
            System.out.println("Price:  $" + room.getPrice());
        } else {
            System.out.println("Room type '" + roomType + "' not found in catalog.");
        }
    }
}
