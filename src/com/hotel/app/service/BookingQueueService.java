package com.hotel.app.service;

import com.hotel.app.model.ReservationRequest;
import java.util.LinkedList;
import java.util.Queue;

public class BookingQueueService {
    private Queue<ReservationRequest> requestQueue;

    public BookingQueueService() {
        this.requestQueue = new LinkedList<>();
    }

    public void submitRequest(ReservationRequest request) {
        requestQueue.add(request);
    }

    public ReservationRequest processNextRequest() {
        return requestQueue.poll();
    }

    public void displayQueue() {
        System.out.println("\n--- Current Booking Request Queue ---");
        int pos = 1;
        for (ReservationRequest req : requestQueue) {
            System.out.println(pos++ + ". " + req.getGuestName() + " (" + req.getRoomType() + ")");
        }
        System.out.println("--------------------------------------\n");
    }
}
