package service;

import model.ReservationRequest;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Use Case 5: Booking Request (First-Come-First-Served)
 * Manages the intake and ordering of booking requests using a Queue.
 */
public class BookingQueueService {
    // Key Concept: Queue - FIFO (First-In-First-Out) ensures fairness.
    private Queue<ReservationRequest> requestQueue;

    public BookingQueueService() {
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Requirement: Accept booking requests from guests.
     * Adds the request to the end of the queue.
     */
    public void submitRequest(ReservationRequest request) {
        requestQueue.add(request);
        System.out.println(">> Request Queued: " + request.getGuestName() + " for a " + request.getRoomType());
    }

    /**
     * Requirement: Prepare requests for subsequent processing.
     * Retrieves but does not remove the next request (Peek).
     */
    public ReservationRequest peekNextRequest() {
        return requestQueue.peek();
    }

    /**
     * Requirement: Display current queue status.
     * Shows the order in which guests will be processed.
     */
    public void displayQueue() {
        System.out.println("\n--- Current Booking Request Queue (FIFO Order) ---");
        if (requestQueue.isEmpty()) {
            System.out.println("[Queue is Empty]");
        } else {
            int position = 1;
            for (ReservationRequest request : requestQueue) {
                System.out.println(position + ". " + request.getGuestName() + " (" + request.getRoomType() + ")");
                position++;
            }
        }
        System.out.println("---------------------------------------------------\n");
    }

    /**
     * Helper: Get total requests in queue.
     */
    public int getQueueSize() {
        return requestQueue.size();
    }

    /**
     * Helper: Process (remove) the next request from the queue.
     */
    public ReservationRequest processNextRequest() {
        return requestQueue.poll();
    }
}
