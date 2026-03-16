import model.Room;
import model.SingleRoom;
import model.DoubleRoom;
import model.SuiteRoom;

/**
 * Use Case 2: Basic Room Types & Static Availability
 * Version: 2.0
 */
public class UseCase2RoomInitialization {

    // Requirement 4: Store room availability using individual variables (Static Availability Representation)
    private static int availableSingleRooms = 15;
    private static int availableDoubleRooms = 8;
    private static int availableSuiteRooms = 3;

    public static void main(String[] args) {
        // Application Startup Header
        System.out.println("***************************************************");
        System.out.println("*                                                 *");
        System.out.println("*        BOOK MY STAY - HOTEL MANAGEMENT          *");
        System.out.println("*            Version 2.0 | Use Case 2             *");
        System.out.println("*                                                 *");
        System.out.println("***************************************************");
        System.out.println();

        // Requirement 3: Initialize room objects in the entry point
        // Demonstrating Polymorphism (Key Concept)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Requirement 5: Display room details to the console
        System.out.println("--- PREDEFINED ROOM TYPES ---");
        System.out.println();
        
        single.displayRoomDetails();
        doubleRoom.displayRoomDetails();
        suite.displayRoomDetails();

        // Requirement 5: Display availability information to the console
        System.out.println("--- CURRENT STATIC AVAILABILITY ---");
        System.out.println("Single Rooms Available: " + availableSingleRooms);
        System.out.println("Double Rooms Available: " + availableDoubleRooms);
        System.out.println("Suite Rooms Available:  " + availableSuiteRooms);
        System.out.println();
        System.out.println("***************************************************");
        System.out.println("Application terminates.");
    }
}
