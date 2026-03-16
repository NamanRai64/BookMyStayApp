import model.ReservationRequest;
import service.BookingQueueService;

/**
 * Use Case 5: Booking Request (First-Come-First-Served)
 * Goal: Demonstrate fair request handling through FIFO ordering.
 */
public class UseCase5BookingRequest {
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("   Book My Stay App - Hotel Management System  ");
        System.out.println("   Version 5.0 | Use Case 5: Booking Queue    ");
        System.out.println("===============================================\n");

        // 1. Initialize the Booking Request Queue (FIFO Mechanism)
        BookingQueueService bookingQueue = new BookingQueueService();

        // 2. Guests submit booking requests in sequence (Requirement 1 & 3)
        System.out.println("Guest A arrives at 10:00 AM");
        bookingQueue.submitRequest(new ReservationRequest("Alice", "Suite"));

        System.out.println("Guest B arrives at 10:02 AM");
        bookingQueue.submitRequest(new ReservationRequest("Bob", "Single"));

        System.out.println("Guest C arrives at 10:05 AM");
        bookingQueue.submitRequest(new ReservationRequest("Charlie", "Suite"));

        System.out.println("Guest D arrives at 10:07 AM");
        bookingQueue.submitRequest(new ReservationRequest("Diana", "Double"));

        // 3. Display the stored requests in arrival order (Requirement 3 & 5)
        // Notice how the arrival order is preserved exactly.
        bookingQueue.displayQueue();

        // 4. Demonstrate FIFO processing behavior
        System.out.println(">> System begins processing requests...");
        
        ReservationRequest next = bookingQueue.processNextRequest();
        System.out.println("Processing: " + next.getGuestName() + " (First in line)");

        next = bookingQueue.processNextRequest();
        System.out.println("Processing: " + next.getGuestName() + " (Second in line)");

        // 5. Display the remaining queue
        System.out.println("\n>> Current Queue after processing two requests:");
        bookingQueue.displayQueue();

        // 6. Ensure no inventory mutation occurred at this stage (Requirement 4)
        System.out.println("Requirement Note: No inventory has been updated yet.");
        System.out.println("Requests are simply queued for the allocation system.");

        System.out.println("\n===============================================");
        System.out.println("Key Benefit: Fairness ensures earlier requests are processed first.");
        System.out.println("Application terminates.");
    }
}
