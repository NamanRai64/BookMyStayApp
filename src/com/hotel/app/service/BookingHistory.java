package com.hotel.app.service;

import com.hotel.app.model.Reservation;
import java.util.ArrayList;
import java.util.List;

/**
 * Use Case 8: Booking History
 * Maintains a record of confirmed reservations in sequential (insertion) order.
 * Key Concept: List Data Structure - Preserves chronological order of transactions.
 * Persistence Mindset - Treats history as long-lived operational data.
 */
public class BookingHistory {
    // Requirement: A List<Reservation> is used to store confirmed bookings.
    private final List<Reservation> history;

    public BookingHistory() {
        this.history = new ArrayList<>();
    }

    /**
     * Requirement: Confirmed reservation is added to booking history.
     */
    public void addRecord(Reservation reservation) {
        history.add(reservation);
        System.out.println(">> System: History Entry Recorded for " + reservation.getGuestName());
    }

    /**
     * Requirement: Allow retrieval of stored reservations for review.
     * Returns a copy to follow "Reporting Readiness" principle (Separation of Data and Logic).
     */
    public List<Reservation> getHistoricalRecords() {
        return new ArrayList<>(history);
    }

    public int getRecordCount() {
        return history.size();
    }
}
