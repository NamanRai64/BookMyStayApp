import model.Room;
import model.SingleRoom;
import model.DoubleRoom;
import model.SuiteRoom;
import service.RoomInventory;
import service.SearchService;

import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 4: Room Search & Availability Check
 * Goal: Enable guests to view available rooms without modifying system state.
 */
public class UseCase4RoomSearch {
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("   Book My Stay App - Hotel Management System  ");
        System.out.println("   Version 4.0 | Use Case 4: Room Search      ");
        System.out.println("===============================================\n");

        // 1. Initialize State (Inventory)
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoomType("Single", 5);
        inventory.registerRoomType("Double", 0); // Sold out for demonstration
        inventory.registerRoomType("Suite", 2);

        // 2. Initialize Catalog (Domain Objects)
        // This mapping allows the search service to bridge inventory keys to rich domain data
        Map<String, Room> catalog = new HashMap<>();
        catalog.put("Single", new SingleRoom());
        catalog.put("Double", new DoubleRoom());
        catalog.put("Suite", new SuiteRoom());

        // 3. Initialize Search Service (Separation of Concerns)
        SearchService searchService = new SearchService(inventory, catalog);

        // 4. Guest initiates a room search request
        System.out.println("Guest: 'What rooms are available today?'");
        searchService.searchAvailableRooms();

        // 5. Demonstrate Defensive Programming / Validation
        // Room "Double" should be filtered out because availability is 0
        System.out.println("Guest: 'Is there a Double Room available?'");
        searchService.displayRoomSummary("Double");

        System.out.println("\nGuest: 'How about a Suite?'");
        searchService.displayRoomSummary("Suite");

        // 6. Demonstrate State Consistency
        // Verify that searching didn't change inventory
        System.out.println("\n>> Verifying System State (Inventory counts should remain unchanged):");
        inventory.displayInventory();

        System.out.println("===============================================");
        System.out.println("Key Benefit: Clear boundary between Read (Search) and Write (Booking) logic.");
        System.out.println("Application terminates.");
    }
}
