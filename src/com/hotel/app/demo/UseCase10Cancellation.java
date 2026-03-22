package com.hotel.app.demo;

import com.hotel.app.exception.*;
import com.hotel.app.model.*;
import com.hotel.app.service.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback Demo
 * Demonstrates:
 * 1. Safe cancellation of confirmed bookings.
 * 2. State reversal (Inventory increment).
 * 3. Stack-based LIFO rollback logic (Released Room IDs).
 * 4. Validation of cancellation requests.
 */
public class UseCase10Cancellation {
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("   Book My Stay - UC 10 Cancellation Rollback ");
        System.out.println("===============================================\n");

        // 1. Initialise Environment
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Suite", 2);
        
        Map<String, Room> catalog = new HashMap<>();
        catalog.put("Suite", new SuiteRoom());

        AllocationService allocationService = new AllocationService(inventory);
        BookingHistory history = new BookingHistory();
        
        // --- NEW SERVICE FOR UC 10 ---
        CancellationService cancellationService = new CancellationService(history, inventory);
        // ------------------------------

        // 2. Perform a few bookings to set the stage
        System.out.println(">> [PHASE 1] Recording Initial Bookings...");
        String id1 = null, id2 = null;
        try {
            id1 = allocationService.allocateRoom(new ReservationRequest("Alice", "Suite"));
            history.addRecord(new Reservation("Alice", "Suite", id1, 350.0));
            
            id2 = allocationService.allocateRoom(new ReservationRequest("Bob", "Suite"));
            history.addRecord(new Reservation("Bob", "Suite", id2, 350.0));
            
            System.out.println("SUCCESS. Initial state: 2 Suites Allocated.");
        } catch (HotelAppException e) {
            System.err.println("PREP FAILED: " + e.getMessage());
        }

        System.out.println("\n>> [PHASE 2] Initial Booking Audit & Inventory Check...");
        inventory.displayInventory();
        System.out.println(">> History Audit Log:");
        history.getHistoricalRecords().forEach(System.out::println);

        // 3. Test Cases for Cancellation
        System.out.println("\n>> [PHASE 3] Initiating Cancellation & Rollback Scenarios...");

        // SCENARIO A: Valid Cancellation (Alice)
        System.out.println("\n--- Scenario A: Valid Cancellation (LIFO Step 1) ---");
        try {
            cancellationService.cancelBooking(id1);
        } catch (HotelAppException e) {
            System.err.println("CATCH (Alice): " + e.getMessage());
        }

        // SCENARIO B: Valid Cancellation (Bob)
        System.out.println("\n--- Scenario B: Valid Cancellation (LIFO Step 2) ---");
        try {
            cancellationService.cancelBooking(id2);
        } catch (HotelAppException e) {
            System.err.println("CATCH (Bob): " + e.getMessage());
        }

        // SCENARIO C: Invalid Cancellation (Unknown ID)
        System.out.println("\n--- Scenario C: Invalid Cancellation (Unknown ID) ---");
        try {
            cancellationService.cancelBooking("X999");
        } catch (HotelAppException e) {
            System.err.println("CATCH (Invalid): Expected Error -> " + e.getMessage());
        }

        // SCENARIO D: Duplicate Cancellation
        System.out.println("\n--- Scenario D: Duplicate Cancellation Attempt ---");
        try {
            cancellationService.cancelBooking(id2);
        } catch (HotelAppException e) {
            System.err.println("CATCH (Duplicate): Expected Error -> " + e.getMessage());
        }

        // 4. Verify Final State
        System.out.println("\n>> [PHASE 4] Final System Audit After Rollback...");
        inventory.displayInventory();
        cancellationService.displayRollbackState();
        System.out.println(">> Final History Log:");
        history.getHistoricalRecords().forEach(System.out::println);

        System.out.println("\n===============================================");
        System.out.println("Key Benefits: Predictable recovery + State consistency.");
        System.out.println("Application terminates normally.");
        System.out.println("===============================================");
    }
}
