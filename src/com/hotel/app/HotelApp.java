package com.hotel.app;

import com.hotel.app.model.*;
import com.hotel.app.service.*;
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

        // Demonstration
        System.out.println("Checking available rooms...");
        searchService.searchAvailableRooms();

        System.out.println("Submitting requests...");
        bookingQueue.submitRequest(new ReservationRequest("Alice", "Suite"));
        bookingQueue.submitRequest(new ReservationRequest("Bob", "Single"));

        bookingQueue.displayQueue();

        System.out.println("System initialized successfully.");
    }
}
