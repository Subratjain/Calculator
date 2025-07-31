package com.parceldelivery.backend.dto;

public class SignupResponse {
    private String message;
    private String customerId;
    private String customerName;
    private String email;

    public SignupResponse(String message, String customerId, String customerName, String email) {
        this.message = message;
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}