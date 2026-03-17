package com.hotel.app.service;

import com.hotel.app.model.AddOnService;
import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 * Manages associations between reservations and selected services.
 */
public class AddOnManager {
    // Key Concept: Map and List Combination (Reservation ID -> List of Services)
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnManager() {
        this.reservationServices = new HashMap<>();
    }

    /**
     * Requirement: Allow multiple services to be attached to a single reservation.
     */
    public void addService(String roomId, AddOnService service) {
        reservationServices.computeIfAbsent(roomId, k -> new ArrayList<>()).add(service);
        System.out.println(">> Service Added: " + service.getName() + " to Room " + roomId);
    }

    /**
     * Requirement: Calculate total additional cost.
     */
    public double calculateTotalAddOnCost(String roomId) {
        List<AddOnService> services = reservationServices.getOrDefault(roomId, Collections.emptyList());
        return services.stream().mapToDouble(AddOnService::getPrice).sum();
    }

    /**
     * Requirement: Show selected services for a reservation.
     */
    public void displayServicesForRoom(String roomId) {
        List<AddOnService> services = reservationServices.get(roomId);
        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for Room " + roomId);
            return;
        }
        System.out.println("Add-ons for " + roomId + ": " + services);
        System.out.println("Total Service Cost: $" + calculateTotalAddOnCost(roomId));
    }
}
