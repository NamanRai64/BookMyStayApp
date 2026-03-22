package com.hotel.app.demo;

import com.hotel.app.exception.*;
import com.hotel.app.model.*;
import com.hotel.app.service.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 * Demonstrates:
 * 1. Thread safety in a multi-user environment.
 * 2. Protection of shared mutable state (Queue and Inventory).
 * 3. Prevention of Race Conditions and Double-Allocation.
 */
public class UseCase11Concurrency {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("===============================================");
        System.out.println("   Book My Stay - UC 11 Concurrent Simulation ");
        System.out.println("===============================================\n");

        // 1. Initialise Shared Resources
        // Requirement: Shared data structures for requests and inventory.
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Suite", 5); // 5 Suites available for simulation
        
        BookingQueueService queueService = new BookingQueueService();
        AllocationService allocationService = new AllocationService(inventory);
        
        // Tracking successes and failures for verification
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger soldOutCount = new AtomicInteger(0);

        // 2. Thread Pool for Concurrent Guests
        // Requirement: Simulate multiple booking requests occurring at the same time.
        int totalRequests = 10;
        ExecutorService executor = Executors.newFixedThreadPool(4); // 4 concurrent threads

        System.out.println(">> [START] Simulating " + totalRequests + " concurrent requests for 5 Suites...");
        System.out.println("Processing...\n");

        for (int i = 1; i <= totalRequests; i++) {
            final String guestName = "Guest-" + i;
            executor.submit(() -> {
                try {
                    // --- STEP 1: Submit Request (Synchronized shared queue) ---
                    ReservationRequest request = new ReservationRequest(guestName, "Suite");
                    queueService.submitRequest(request);
                    
                    // --- STEP 2: Safe Room Allocation (Synchronized shared inventory) ---
                    // Requirement: Inventory updates are performed in a thread-safe manner.
                    String roomId = allocationService.allocateRoom(request);
                    
                    if (roomId != null) {
                        successCount.incrementAndGet();
                        System.out.println("[Thread " + Thread.currentThread().getId() + "] SUCCESS: " + guestName + " -> Allocated " + roomId);
                    }
                } catch (NoAvailabilityException e) {
                    soldOutCount.incrementAndGet();
                    System.out.println("[Thread " + Thread.currentThread().getId() + "] REJECTED: " + guestName + " -> (Sold Out)");
                } catch (Exception e) {
                    System.out.println("[Thread " + Thread.currentThread().getId() + "] SYSTEM ERROR: " + e.getMessage());
                }
            });
        }

        // 3. Graceful Shutdown & Completion
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        // 4. Verification & Consistency Check
        // Requirement: Complete allocations without conflicts or inconsistencies.
        System.out.println("\n>> [SIMULATION COMPLETE] Global Consistency Audit:");
        System.out.println("Initial Stock:           5 Suites");
        System.out.println("Successful Allocations:  " + successCount.get());
        System.out.println("Sold-out Rejections:     " + soldOutCount.get());
        
        // Requirement: Prevent double allocation under concurrent execution.
        // Safety Check: Total successes MUST NOT exceed initial inventory
        if (successCount.get() <= 5) {
            System.out.println("\nVERIFICATION: SUCCESS. System maintained data integrity under load.");
            System.out.println("Reason: Critical Sections prevented double-booking of shared resources.");
        } else {
            System.err.println("\nVERIFICATION: FAILED! Double-allocation detected (" + successCount.get() + " > 5).");
        }

        inventory.displayInventory();
        
        System.out.println("===============================================");
        System.out.println("Key Benefits: Foundation for building scalable, multi-user systems.");
        System.out.println("Application terminates normally.");
        System.out.println("===============================================");
    }
}
