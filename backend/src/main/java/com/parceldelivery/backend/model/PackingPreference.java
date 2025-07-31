package com.parceldelivery.backend.model;

public enum PackingPreference {
    BASIC("Basic Packing", 10.0),
    PREMIUM("Premium Packing", 30.0);

    private final String displayName;
    private final double cost;

    PackingPreference(String displayName, double cost) {
        this.displayName = displayName;
        this.cost = cost;
    }

    public String getDisplayName() { return displayName; }
    public double getCost() { return cost; }
}