package com.hotel.app.demo;

import com.hotel.app.exception.NoAvailabilityException;
import com.hotel.app.model.*;
import com.hotel.app.service.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 8: Booking History & Reporting Demo
 * Demonstrates:
 * 1. Historical tracking of confirmed reservations.
 * 2. Reporting on stored data by Admin.
 * 3. Operational visibility and audits.
 */
public class UseCase8HistoryAndReporting {
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("   Book My Stay - UC 8 History & Reporting   ");
        System.out.println("===============================================\n");

        // 1. Initialise Environment
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Suite", 2);
        inventory.registerRoomType("Single", 5);
        
        Map<String, Room> catalog = new HashMap<>();
        catalog.put("Suite", new SuiteRoom());
        catalog.put("Single", new SingleRoom());

        AllocationService allocationService = new AllocationService(inventory);
        BookingQueueService requestQueue = new BookingQueueService();
        
        // --- NEW SERVICES FOR UC 8 ---
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();
        // -----------------------------

        // 2. Add some requests to the queue (FIFO)
        System.out.println(">> [PHASE 1] Submitting requests to queue...");
        requestQueue.submitRequest(new ReservationRequest("Alice", "Suite"));
        requestQueue.submitRequest(new ReservationRequest("Bob", "Single"));
        requestQueue.submitRequest(new ReservationRequest("Charlie", "Suite"));
        requestQueue.submitRequest(new ReservationRequest("David", "Suite")); // Note: Only 2 Suites available

        // 3. Process Requests (Allocation -> History)
        System.out.println("\n>> [PHASE 2] Processing requests and creating historical records...");
        
        while (true) {
            ReservationRequest nextReq = requestQueue.processNextRequest();
            if (nextReq == null) break;

            System.out.print("Processing Guest: " + nextReq.getGuestName() + " | ");
            try {
                String assignedRoom = allocationService.allocateRoom(nextReq);
                System.out.println("SUCCESS. Assigned to " + assignedRoom);
                
                // --- Requirement: Store each confirmed reservation in booking history ---
                Room roomDetails = catalog.get(nextReq.getRoomType());
                Reservation res = new Reservation(
                    nextReq.getGuestName(), 
                    nextReq.getRoomType(), 
                    assignedRoom, 
                    roomDetails.getPrice()
                );
                
                // Simulate add-ons for specific guests (integration with UC 7 concepts)
                if (nextReq.getGuestName().equals("Alice")) {
                    System.out.println("   (System: Adding Late Checkout service to record)");
                    res.addAddOn(new AddOnService("Late Checkout", 25.0));
                    res.addAddOn(new AddOnService("Breakfast Buffet", 15.0));
                }
                
                // --- Requirement: Confirmed reservation is added to history ---
                history.addRecord(res);
            } catch (NoAvailabilityException e) {
                System.out.println("FAILED. " + e.getMessage());
            }
        }

        // 4. Admin Reporting Phase 
        // --- Requirement: Admin requests booking information or reports ---
        System.out.println("\n>> [PHASE 3] Admin Review: Historical Audits & Reporting...");
        
        // --- Requirement: Allow retrieval of stored reservations for review ---
        reportService.displayDetailedAuditLog(history.getHistoricalRecords());
        
        // --- Requirement: Generate summary reports from booking history ---
        reportService.generateSummaryReport(history.getHistoricalRecords());

        System.out.println("===============================================");
        System.out.println("Key Benefits: Full Audit Trail & Operational Visibility.");
        System.out.println("Application terminates.");
    }
}
