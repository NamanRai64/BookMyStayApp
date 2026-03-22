package com.hotel.app;

import com.hotel.app.exception.HotelAppException;
import com.hotel.app.model.*;
import com.hotel.app.service.*;
import java.io.IOException;
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
        BookingHistory bookingHistory = new BookingHistory(); // UC 8
        BookingReportService reportService = new BookingReportService(); // UC 8
        BookingValidator validator = new BookingValidator(inventory); // UC 9
        CancellationService cancellationService = new CancellationService(bookingHistory, inventory); // UC 10
        PersistenceService persistenceStorage = new PersistenceService("hotel_final_state.ser"); // UC 12






        // --- Use Case 12: Recovery Phase (Phase 0) ---
        System.out.println(gold + "\n>> [PHASE 0] System Startup: Recovering Previous State..." + reset);
        try {
            SystemState recoveredState = persistenceStorage.loadState();
            if (recoveredState != null) {
                bookingHistory.restoreHistoricalRecords(recoveredState.getHistory());
                inventory.restoreFromSnapshot(recoveredState.getInventory());
                System.out.println(cyan + "RESULT: System fully recovered from last known snapshot." + reset);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("RECOVERY FAILED (I/O Error): " + e.getMessage());
        }

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

            // --- Use Case 9: Input & State Validation (Fail-Fast) ---
            try {
                validator.validateRequest(nextReq);
                
                // Logical Unit: Check availability (internal guard) -> Decrement -> Assign ID -> Record
                String assignedRoomId = allocationService.allocateRoom(nextReq);

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

                // --- Use Case 8: Booking History Recording ---
                Room roomInfo = catalog.get(nextReq.getRoomType());
                Reservation res = new Reservation(nextReq.getGuestName(), nextReq.getRoomType(), assignedRoomId, roomInfo.getPrice());
                
                // Mirror add-ons from UC 7 to History Record (Simplification for simulation)
                if (nextReq.getGuestName().equals("Alice")) {
                    res.addAddOn(new AddOnService("Late Checkout", 25.0));
                    res.addAddOn(new AddOnService("Breakfast Buffet", 15.0));
                } else if (nextReq.getGuestName().equals("Bob")) {
                    res.addAddOn(new AddOnService("Mini Bar", 30.0));
                } else if (nextReq.getGuestName().equals("David")) {
                    res.addAddOn(new AddOnService("Spa Access", 60.0));
                }

                bookingHistory.addRecord(res);
            } catch (HotelAppException e) {
                System.err.println("RESULT: FAILED. " + e.getMessage());
            }
        }

        // --- Final System State Audit ---
        System.out.println(gold + "\n>> [PHASE 4] Final System Audit & Synchronization Check:" + reset);
        allocationService.displayAllocations();
        inventory.displayInventory();

        System.out.println(gold + "\n>> [PHASE 5] Admin Historical Reports & Analytics:" + reset);
        reportService.generateSummaryReport(bookingHistory.getHistoricalRecords());

        // --- Use Case 10: Booking Cancellation & State Rollback ---
        System.out.println(gold + "\n>> [PHASE 6] Booking Cancellation & System Rollback:" + reset);
        try {
            // Simulating a guest (Alice) cancelling her booking (S101)
            System.out.println(">> Guest (Alice) initiating cancellation [REQ TYPE: Rollback]...");
            cancellationService.cancelBooking("S101");
            
            // Simulating an invalid cancellation attempt
            System.out.println("\n>> Guest attempting an invalid cancellation (Unknown ID)...");
            cancellationService.cancelBooking("X999");
        } catch (HotelAppException e) {
            System.err.println("CATCH: " + e.getMessage());
        }

        // --- Final System State Audit ---
        System.out.println(gold + "\n>> [FINAL] System Audit & Rollback Verification:" + reset);
        allocationService.displayAllocations();
        inventory.displayInventory();
        cancellationService.displayRollbackState();
        
        // --- Use Case 12: Persistence Phase (Phase 7) ---
        System.out.println(gold + "\n>> [PHASE 7] Final System State Snapshot & Persistence..." + reset);
        try {
            SystemState finalSnap = new SystemState(bookingHistory.getHistoricalRecords(), inventory.getInventorySnapshot());
            persistenceStorage.saveState(finalSnap);
        } catch (IOException e) {
            System.err.println("PERSISTENCE FAILED (I/O Error): " + e.getMessage());
        }

        System.out.println(gold + border + reset);
        System.out.println(cyan + "Module 10 (Cancellation & Rollback) verification complete." + reset);
        System.out.println("Application terminating normally.");


    }
}
