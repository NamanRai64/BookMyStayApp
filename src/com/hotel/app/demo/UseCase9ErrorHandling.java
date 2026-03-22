package com.hotel.app.demo;

import com.hotel.app.exception.*;
import com.hotel.app.model.ReservationRequest;
import com.hotel.app.service.*;

/**
 * Use Case 9: Error Handling & Validation Demo
 * Demonstrates:
 * 1. Structured validation for room types and availability.
 * 2. Custom exceptions for invalid system states.
 * 3. Graceful failure handling and "Fail-Fast" behavior.
 */
public class UseCase9ErrorHandling {
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("   Book My Stay - UC 9 Error Handling    ");
        System.out.println("===============================================\n");

        // 1. Initialise Environment
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Single", 1); // Only 1 single room
        
        BookingValidator validator = new BookingValidator(inventory);
        AllocationService allocationService = new AllocationService(inventory);

        // --- SCENARIO 1: Invalid Room Type ---
        System.out.println(">> [SCENARIO 1] Requesting a non-existent room type...");
        ReservationRequest invalidTypeReq = new ReservationRequest("Alice", "Penthouse");
        try {
            validator.validateRequest(invalidTypeReq);
        } catch (HotelAppException e) {
            System.err.println("CATCH: " + e.getMessage());
        }

        // --- SCENARIO 2: Valid Request (Success) ---
        System.out.println("\n>> [SCENARIO 2] Processing a valid request...");
        ReservationRequest validReq = new ReservationRequest("Bob", "Single");
        try {
            validator.validateRequest(validReq);
            String roomId = allocationService.allocateRoom(validReq);
            System.out.println("RESULT: Success. Room Allocated: " + roomId);
        } catch (HotelAppException e) {
            System.err.println("CATCH: Unexpected failure: " + e.getMessage());
        }

        // --- SCENARIO 3: No Availability (State Violation) ---
        System.out.println("\n>> [SCENARIO 3] Requesting a room that is sold out...");
        ReservationRequest soldOutReq = new ReservationRequest("Charlie", "Single");
        try {
            validator.validateRequest(soldOutReq); // Should fail here (Fail-Fast)
            allocationService.allocateRoom(soldOutReq);
        } catch (HotelAppException e) {
            System.err.println("CATCH: " + e.getMessage());
        }

        // --- SCENARIO 4: Bypassing Validator (Testing System Guards) ---
        System.out.println("\n>> [SCENARIO 4] Attempting allocation without validation (System Guard test)...");
        try {
            // Even if we skip validation, AllocationService/Inventory should guard the state
            allocationService.allocateRoom(soldOutReq); 
        } catch (NoAvailabilityException e) {
            System.err.println("CATCH (GUARD): " + e.getMessage());
        }

        System.out.println("\n===============================================");
        System.out.println("Key Benefits: Early detection + System state protection.");
        System.out.println("Application stable after multiple failure scenarios.");
        System.out.println("===============================================");
    }
}
