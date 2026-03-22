package com.hotel.app.demo;

import com.hotel.app.exception.*;
import com.hotel.app.model.*;
import com.hotel.app.service.*;

import java.io.File;
import java.io.IOException;

/**
 * Use Case 12: Data Persistence & System Recovery Demo
 * Demonstrates:
 * 1. Serialization of Booking History and Inventory.
 * 2. Saving system state to a durable file.
 * 3. Restoring state after an "application restart" (simulated).
 */
public class UseCase12Persistence {
    private static final String DATA_FILE = "hotel_state.ser";

    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("   Book My Stay - UC 12 Data Persistence      ");
        System.out.println("===============================================\n");

        // Clean up previous test runs to ensure fresh demo
        File file = new File(DATA_FILE);
        if (file.exists()) file.delete();

        // --- PHASE 1: Initial Run & State Creation ---
        System.out.println(">> [PHASE 1] Initial Setup & State Creation...");
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Double", 5);
        
        BookingHistory history = new BookingHistory();
        PersistenceService persistence = new PersistenceService(DATA_FILE);

        // Requirement: Add confirmed reservation to history before persistence.
        history.addRecord(new Reservation("Alice", "Double", "D101", 150.0));
        try {
            inventory.updateAvailability("Double", -1); // Alice allocated
        } catch (NoAvailabilityException e) {
            System.err.println("Setup Error: " + e.getMessage());
        }

        System.out.println("State Created: 1 Booking, 4 'Double' rooms remaining.");
        inventory.displayInventory();

        // --- PHASE 2: Persist State (Serialization) ---
        // Requirement: Serialize state into persistent format and write to file.
        System.out.println("\n>> [PHASE 2] Persisting System State for Shutdown Snapshot...");
        SystemState snapshot = new SystemState(history.getHistoricalRecords(), inventory.getInventorySnapshot());
        try {
            persistence.saveState(snapshot);
        } catch (IOException e) {
            System.err.println("CRITICAL: Persistence Save Failed! " + e.getMessage());
            return;
        }

        // --- PHASE 3: Clear Memory (Simulated Restart) ---
        // Requirement: Transition from in-memory only to durable design.
        System.out.println("\n>> [PHASE 3] Simulating Application Termination & Memory Wipe...");
        inventory = new RoomInventory(); // New Instance (Empty)
        history = new BookingHistory();   // New Instance (Empty)
        System.out.println("Current Memory Status: [RESET] No active bookings or inventory found.");

        // --- PHASE 4: Restore State (Deserialization) ---
        // Requirement: Restore persisted data during application startup.
        System.out.println("\n>> [PHASE 4] Recovering State from Durable Storage...");
        try {
            SystemState recoveredState = persistence.loadState();
            if (recoveredState != null) {
                history.restoreHistoricalRecords(recoveredState.getHistory());
                inventory.restoreFromSnapshot(recoveredState.getInventory());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("CRITICAL: System Recovery Failed! " + e.getMessage());
            return;
        }

        // --- PHASE 5: Verification Post-Recovery ---
        // Requirement: Restored state must accurately reflect the last saved state.
        System.out.println("\n>> [PHASE 5] Final Audit & Continuity Check:");
        inventory.displayInventory();
        System.out.println("Recovered Booking Record Count: " + history.getRecordCount());
        history.getHistoricalRecords().forEach(System.out::println);

        if (history.getRecordCount() == 1 && inventory.getAvailability("Double") == 4) {
            System.out.println("\nVERIFICATION: SUCCESS. Integrity maintained across system restart.");
        } else {
            System.err.println("\nVERIFICATION: FAILED. Recovered state is inconsistent.");
        }

        System.out.println("\n===============================================");
        System.out.println("Key Benefits: Durable continuity & Reliability.");
        System.out.println("Application terminates normally.");
        System.out.println("===============================================");
    }
}
