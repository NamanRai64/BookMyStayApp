package com.hotel.app.service;

import com.hotel.app.model.Reservation;
import java.util.List;

/**
 * Use Case 8: Booking Report Service
 * Generates summaries and reports from stored booking data.
 * Key Concept: Separation of Data Storage and Reporting - History stores, Report service analyzes.
 * Requirement: Admin reviews booking history and reports.
 */
public class BookingReportService {

    /**
     * Requirement: Generate summary reports from booking history. 
     * Requirement: Reporting does not modify stored booking data.
     */
    public void generateSummaryReport(List<Reservation> history) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   ADMINISTRATIVE BOOKING REPORT: ALL-TIME HISTORY");
        System.out.println("=".repeat(60) + "\n");

        if (history.isEmpty()) {
            System.out.println("No booking records available to report.");
            return;
        }

        double totalRevenue = 0;
        int count = 0;

        System.out.printf("%-4s | %-12s | %-12s | %-10s | %-10s\n", 
            "ID", "Guest", "Room Type", "Room ID", "Total ($)");
        System.out.println("-".repeat(60));

        for (Reservation res : history) {
            count++;
            double cost = res.getTotalCost();
            totalRevenue += cost;
            System.out.printf("%-4d | %-12s | %-12s | %-10s | %-10.2f\n", 
                count, res.getGuestName(), res.getRoomType(), res.getRoomId(), cost);
        }

        System.out.println("-".repeat(60));
        System.out.printf("SUMMARY STATISTICS:\n");
        System.out.printf("Total Reservations: %d\n", count);
        System.out.printf("Total Gross Revenue: $%.2f\n", totalRevenue);
        System.out.printf("Average Value:       $%.2f\n", (totalRevenue / count));
        System.out.println("=".repeat(60) + "\n");
    }

    /**
     * Detailed View for operational visibility.
     */
    public void displayDetailedAuditLog(List<Reservation> history) {
        System.out.println(">> System: Generating Detailed Opertional Audit Audit...");
        for (Reservation res : history) {
            System.out.println(res);
            if (!res.getAddOns().isEmpty()) {
                System.out.println("   - Services: " + res.getAddOns());
            }
        }
    }
}
