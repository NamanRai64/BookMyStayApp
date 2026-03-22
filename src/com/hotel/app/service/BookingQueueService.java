package com.hotel.app.service;

import com.hotel.app.model.ReservationRequest;
import java.util.LinkedList;
import java.util.Queue;

public class BookingQueueService {
    private final Queue<ReservationRequest> requestQueue;

    public BookingQueueService() {
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Requirement: Use synchronized access to shared shared queue.
     */
    public synchronized void submitRequest(ReservationRequest request) {
        requestQueue.add(request);
    }

    /**
     * Requirement: Critical Section for atomic polling.
     */
    public synchronized ReservationRequest processNextRequest() {
        return requestQueue.poll();
    }

    public synchronized void displayQueue() {
        System.out.println("\n--- Current Booking Request Queue ---");
        int pos = 1;
        for (ReservationRequest req : requestQueue) {
            System.out.println(pos++ + ". " + req.getGuestName() + " (" + req.getRoomType() + ")");
        }
        System.out.println("--------------------------------------\n");
    }

    public synchronized int getQueueSize() {
        return requestQueue.size();
    }
}
