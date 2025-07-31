package com.parceldelivery.backend.dto;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String customerId;
    private String customerName;
    private String email;
    private String role;

    public JwtResponse(String accessToken, String customerId, String customerName, String email, String role) {
        this.token = accessToken;
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getAccessToken() { return token; }
    public void setAccessToken(String accessToken) { this.token = accessToken; }

    public String getTokenType() { return type; }
    public void setTokenType(String tokenType) { this.type = tokenType; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}