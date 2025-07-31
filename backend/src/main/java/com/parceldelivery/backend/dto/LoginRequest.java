package com.parceldelivery.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String customerId;

    @NotBlank
    private String password;

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}