package com.parceldelivery.backend.controller;

import com.parceldelivery.backend.dto.MessageResponse;
import com.parceldelivery.backend.model.*;
import com.parceldelivery.backend.security.UserPrincipal;
import com.parceldelivery.backend.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('OFFICER')")
    public ResponseEntity<?> createBooking(@Valid @RequestBody Booking booking, Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            boolean isOfficer = userPrincipal.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_OFFICER"));
            
            Booking createdBooking = bookingService.createBooking(
                    userPrincipal.getCustomerId(), 
                    booking, 
                    isOfficer
            );
            
            return ResponseEntity.ok(createdBooking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @PostMapping("/calculate-cost")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('OFFICER')")
    public ResponseEntity<?> calculateCost(@RequestBody CostCalculationRequest request, Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            boolean isOfficer = userPrincipal.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_OFFICER"));
            
            BigDecimal cost = bookingService.calculateServiceCost(
                    request.getWeightInGram(),
                    request.getDeliveryType(),
                    request.getPackingPreference(),
                    isOfficer
            );
            
            return ResponseEntity.ok(new CostCalculationResponse(cost));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/my-bookings")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getMyBookings(Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            List<Booking> bookings = bookingService.getBookingsByCustomer(userPrincipal.getCustomerId());
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<?> getAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/track/{bookingId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('OFFICER')")
    public ResponseEntity<?> trackBooking(@PathVariable String bookingId, Authentication authentication) {
        try {
            Optional<Booking> booking = bookingService.getBookingByBookingId(bookingId);
            if (!booking.isPresent()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Booking not found"));
            }

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            boolean isOfficer = userPrincipal.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_OFFICER"));

            // If customer, only allow tracking own bookings
            if (!isOfficer && !booking.get().getCustomer().getCustomerId().equals(userPrincipal.getCustomerId())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Access denied"));
            }

            return ResponseEntity.ok(booking.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    @PutMapping("/status/{bookingId}")
    @PreAuthorize("hasRole('OFFICER')")
    public ResponseEntity<?> updateBookingStatus(@PathVariable String bookingId, @RequestBody BookingStatusRequest request) {
        try {
            Booking updatedBooking = bookingService.updateBookingStatus(bookingId, request.getStatus());
            return ResponseEntity.ok(updatedBooking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    // DTOs for requests and responses
    public static class CostCalculationRequest {
        private Integer weightInGram;
        private DeliveryType deliveryType;
        private PackingPreference packingPreference;

        // Getters and Setters
        public Integer getWeightInGram() { return weightInGram; }
        public void setWeightInGram(Integer weightInGram) { this.weightInGram = weightInGram; }

        public DeliveryType getDeliveryType() { return deliveryType; }
        public void setDeliveryType(DeliveryType deliveryType) { this.deliveryType = deliveryType; }

        public PackingPreference getPackingPreference() { return packingPreference; }
        public void setPackingPreference(PackingPreference packingPreference) { this.packingPreference = packingPreference; }
    }

    public static class CostCalculationResponse {
        private BigDecimal cost;

        public CostCalculationResponse(BigDecimal cost) {
            this.cost = cost;
        }

        public BigDecimal getCost() { return cost; }
        public void setCost(BigDecimal cost) { this.cost = cost; }
    }

    public static class BookingStatusRequest {
        private BookingStatus status;

        public BookingStatus getStatus() { return status; }
        public void setStatus(BookingStatus status) { this.status = status; }
    }
}