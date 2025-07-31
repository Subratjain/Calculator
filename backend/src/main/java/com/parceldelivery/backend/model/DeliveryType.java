package com.parceldelivery.backend.model;

public enum DeliveryType {
    STANDARD("Standard Delivery", 30.0),
    EXPRESS("Express Delivery", 80.0),
    SAME_DAY("Same-Day Delivery", 150.0);

    private final String displayName;
    private final double cost;

    DeliveryType(String displayName, double cost) {
        this.displayName = displayName;
        this.cost = cost;
    }

    public String getDisplayName() { return displayName; }
    public double getCost() { return cost; }
}