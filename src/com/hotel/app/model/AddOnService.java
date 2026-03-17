package com.hotel.app.model;

/**
 * Use Case 7: Add-On Service Selection
 * Represents an optional service a guest can add to their stay.
 */
public class AddOnService {
    private String name;
    private double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}
