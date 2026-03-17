package com.hotel.app;

import com.hotel.app.model.*;
import com.hotel.app.service.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Main Entry Point for Book My Stay App
 */
public class HotelApp {
    public static void main(String[] args) {
        // ANSI Colors for a premium hotel feel
        String gold = "\u001B[33m";
        String cyan = "\u001B[36m";
        String reset = "\u001B[0m";

        String logo = "██████╗  ██████╗  ██████╗ ██╗  ██╗    ███╗   ███╗██╗   ██╗    ███████╗████████╗ █████╗ ██╗   ██╗\n"
                +
                "██╔══██╗██╔═══██╗██╔═══██╗██║ ██╔╝    ████╗ ████║╚██╗ ██╔╝    ██╔════╝╚══██╔══╝██╔══██╗╚██╗ ██╔╝\n" +
                "██████╔╝██║   ██║██║   ██║█████╔╝     ██╔████╔██║ ╚████╔╝     ███████╗   ██║   ███████║ ╚████╔╝ \n" +
                "██╔══██╗██║   ██║██║   ██║██╔═██╗     ██║╚██╔╝██║  ╚██╔╝      ╚════██║   ██║   ██╔══██║  ╚██╔╝  \n" +
                "██████╔╝╚██████╔╝╚██████╔╝██║  ██╗    ██║ ╚═╝ ██║   ██║       ███████║   ██║   ██║  ██║   ██║   \n" +
                "╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝    ╚═╝     ╚═╝   ╚═╝       ╚══════╝   ╚═╝   ╚═╝  ╚═╝   ╚═╝   ";

        String border = "══════════════════════════════════════════════════════════════════════════════════════════════";

        System.out.println(gold + border + reset);
        System.out.println(cyan + logo + reset);
        System.out.println(gold + border + reset);
        System.out.println("\n[ Welcome to Book My Stay | Version 1.0.4 | SRM District ]");

        // Initialize Services
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Single", 10);
        inventory.registerRoomType("Double", 5);
        inventory.registerRoomType("Suite", 2);

        Map<String, Room> catalog = new HashMap<>();
        catalog.put("Single", new SingleRoom());
        catalog.put("Double", new DoubleRoom());
        catalog.put("Suite", new SuiteRoom());

        SearchService searchService = new SearchService(inventory, catalog);
        BookingQueueService bookingQueue = new BookingQueueService();
        AllocationService allocationService = new AllocationService(inventory);
        AddOnManager addOnManager = new AddOnManager();

        // --- Use Case 3 & 4: Room Discovery & Initialization ---
        System.out.println(cyan + "\n>> [PHASE 1] Initializing Room Inventory & Catalog..." + reset);
        searchService.searchAvailableRooms();

        // --- Use Case 5: Request Management (FIFO) ---
        System.out.println(cyan + "\n>> [PHASE 2] Submitting Booking Requests (FIFO Ordering)..." + reset);
        bookingQueue.submitRequest(new ReservationRequest("Alice", "Suite"));
        bookingQueue.submitRequest(new ReservationRequest("Bob", "Single"));
        bookingQueue.submitRequest(new ReservationRequest("Charlie", "Suite")); // Note: Only 2 Suites available
        bookingQueue.submitRequest(new ReservationRequest("David", "Double"));
        bookingQueue.submitRequest(new ReservationRequest("Eve", "Single"));

        bookingQueue.displayQueue();

        // --- Use Case 6: Room Allocation & Double-Booking Prevention ---
        System.out.println(gold + "\n>> [PHASE 3] Processing Queue: Allocation & Uniqueness Enforcement..." + reset);
        while (true) {
            ReservationRequest nextReq = bookingQueue.processNextRequest();
            if (nextReq == null) break;

            System.out.println("\n------------------------------------------------");
            System.out.println("Processing Guest: " + nextReq.getGuestName());
            System.out.println("Requested Type:  " + nextReq.getRoomType());

            // Logical Unit: Check availability -> Decrement -> Assign ID -> Record
            String assignedRoomId = allocationService.allocateRoom(nextReq);

            if (assignedRoomId != null) {
                System.out.println(cyan + "RESULT: SUCCESS. Confirmation ID generated." + reset);
                System.out.println("Assigned Room:   " + gold + assignedRoomId + reset);
                
                // --- Use Case 7: Add-On Service Selection (Extensibility) ---
                // Simulating guest choices for specific guests
                if (nextReq.getGuestName().equals("Alice")) {
                    System.out.println(">> Guest selecting optional add-ons...");
                    addOnManager.addService(assignedRoomId, new AddOnService("Late Checkout", 25.0));
                    addOnManager.addService(assignedRoomId, new AddOnService("Breakfast Buffet", 15.0));
                } else if (nextReq.getGuestName().equals("Bob")) {
                    System.out.println(">> Guest selecting optional add-ons...");
                    addOnManager.addService(assignedRoomId, new AddOnService("Mini Bar", 30.0));
                } else if (nextReq.getGuestName().equals("David")) {
                    System.out.println(">> Guest selecting optional add-ons...");
                    addOnManager.addService(assignedRoomId, new AddOnService("Spa Access", 60.0));
                }

                // Cost Aggregation & Service Display
                addOnManager.displayServicesForRoom(assignedRoomId);
            } else {
                System.err.println("RESULT: FAILED. No '" + nextReq.getRoomType() + "' rooms available.");
            }
        }

        // --- Final System State Audit ---
        System.out.println(gold + "\n>> [PHASE 4] Final System Audit & Synchronization Check:" + reset);
        allocationService.displayAllocations();
        inventory.displayInventory();

        System.out.println(gold + border + reset);
        System.out.println(cyan + "Module 6 (Allocation) and Module 7 (Add-Ons) verification complete." + reset);
        System.out.println("Application terminating normally.");
    }
}
