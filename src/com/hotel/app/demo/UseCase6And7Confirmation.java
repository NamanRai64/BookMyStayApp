package com.hotel.app.demo;

import com.hotel.app.model.*;
import com.hotel.app.service.*;

/**
 * Combined Demo for Use Case 6 & 7
 * Demonstrates:
 * 1. FIFO Request Processing
 * 2. Safe Room Allocation (Set-based uniqueness)
 * 3. Add-on Service Selection
 */
public class UseCase6And7Confirmation {
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("   Book My Stay - UC 6 Allocation & UC 7 Add-Ons");
        System.out.println("===============================================\n");

        // 1. Setup Environment
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Suite", 2);
        inventory.registerRoomType("Single", 10);

        AllocationService allocationService = new AllocationService(inventory);
        BookingQueueService requestQueue = new BookingQueueService();
        AddOnManager addOnManager = new AddOnManager();

        // 2. Add some requests to the queue (FIFO)
        requestQueue.submitRequest(new ReservationRequest("Alice", "Suite"));
        requestQueue.submitRequest(new ReservationRequest("Bob", "Suite"));
        requestQueue.submitRequest(new ReservationRequest("Charlie", "Suite")); // Third suite (should fail)

        // 3. Process Requests (Use Case 6: Allocation)
        System.out.println("\n>> Processing requests from queue...");
        
        while (true) {
            ReservationRequest nextReq = requestQueue.processNextRequest();
            if (nextReq == null) break;

            System.out.println("Processing " + nextReq.getGuestName() + "...");
            String assignedRoom = allocationService.allocateRoom(nextReq);

            if (assignedRoom != null) {
                System.out.println("SUCCESS: " + nextReq.getGuestName() + " assigned to " + assignedRoom);
                
                // 4. Handle Add-ons (Use Case 7)
                if (nextReq.getGuestName().equals("Alice")) {
                    addOnManager.addService(assignedRoom, new AddOnService("Late Checkout", 25.0));
                    addOnManager.addService(assignedRoom, new AddOnService("Breakfast Buffet", 15.0));
                    addOnManager.displayServicesForRoom(assignedRoom);
                }
            } else {
                System.out.println("FAILED: No availability for " + nextReq.getGuestName());
            }
        }

        // 5. Verify State
        System.out.println("\n>> Final System State:");
        allocationService.displayAllocations();
        inventory.displayInventory();

        System.out.println("===============================================");
        System.out.println("Key Benefits: Unique allocation + Easy service expansion.");
        System.out.println("Application terminates.");
    }
}
