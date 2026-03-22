package com.hotel.app.service;

import com.hotel.app.model.Reservation;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Use Case 12: Data Persistence & System Recovery
 * Encapsulates the complete system state for serialisation.
 * Key Concept: Serialization - Converts complex objects into a format for storage.
 */
public class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Requirement: Persist booking history and inventory state.
    private final List<Reservation> history;
    private final Map<String, Integer> inventory;

    public SystemState(List<Reservation> history, Map<String, Integer> inventory) {
        this.history = history;
        this.inventory = inventory;
    }

    public List<Reservation> getHistory() { return history; }
    public Map<String, Integer> getInventory() { return inventory; }
}
