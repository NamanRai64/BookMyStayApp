import model.Room;
import model.SingleRoom;
import model.DoubleRoom;
import model.SuiteRoom;
import service.RoomInventory;

/**
 * Use Case 3: Centralized Room Inventory Management
 * Goal: Demonstrate how a HashMap provides a single source of truth for inventory.
 */
public class UseCase3InventoryManagement {
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("   Book My Stay App - Hotel Management System  ");
        System.out.println("   Version 3.0 | Use Case 3: Centralized Inv   ");
        System.out.println("===============================================\n");

        // 1. Initialize the inventory component
        RoomInventory inventory = new RoomInventory();

        // 2. Room types are registered with their available counts (Requirements 1 & 2)
        inventory.registerRoomType("Single", 10);
        inventory.registerRoomType("Double", 5);
        inventory.registerRoomType("Suite", 2);

        // 3. Display Initial Inventory
        System.out.println(">> Initial Inventory State:");
        inventory.displayInventory();

        // 4. Perform controlled updates (Requirement 4)
        System.out.println(">> Processing some bookings and cancellations...");
        
        // Simulating a booking for a Single room (Decrease by 1)
        boolean singleBooked = inventory.updateAvailability("Single", -1);
        System.out.println("Action: Book Single Room  | Status: " + (singleBooked ? "Success" : "Failed"));

        // Simulating a booking for a Suite (Decrease by 1)
        boolean suiteBooked = inventory.updateAvailability("Suite", -1);
        System.out.println("Action: Book Suite Room   | Status: " + (suiteBooked ? "Success" : "Failed"));

        // Simulating a cancellation for a Double room (Increase by 1)
        boolean doubleCancelled = inventory.updateAvailability("Double", 1);
        System.out.println("Action: Cancel Double Room| Status: " + (doubleCancelled ? "Success" : "Failed"));

        // Simulating an overbooking attempt (Should fail)
        System.out.println("Action: Attempt Overbook Suite...");
        inventory.updateAvailability("Suite", -1); // Currently 1 left
        boolean overbooked = inventory.updateAvailability("Suite", -1); // Attempt to book 3rd suite
        System.out.println("Action: Book 3rd Suite    | Status: " + (overbooked ? "Success" : "Failed (Insufficient Inventory)"));

        // 5. Display the current inventory state (Requirement 5)
        System.out.println("\n>> Final Inventory State:");
        inventory.displayInventory();

        System.out.println("===============================================");
        System.out.println("Key Benefit: HashMap provides O(1) lookup and a Single Source of Truth.");
        System.out.println("Application terminates.");
    }
}
