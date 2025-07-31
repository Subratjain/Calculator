package com.parceldelivery.backend.service;

import com.parceldelivery.backend.model.*;
import com.parceldelivery.backend.repository.BookingRepository;
import com.parceldelivery.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    private static final double BASE_RATE = 50.0;
    private static final double WEIGHT_CHARGE_PER_GRAM = 0.02;
    private static final double TAX_RATE = 0.05;
    private static final double ADMIN_FEE = 50.0;

    public Booking createBooking(String customerId, Booking bookingData, boolean isOfficerBooking) {
        Optional<User> userOpt = userRepository.findByCustomerId(customerId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User customer = userOpt.get();
        bookingData.setCustomer(customer);
        bookingData.setBookingId(generateBookingId());
        bookingData.setParcelPaymentTime(LocalDateTime.now());
        
        // Calculate service cost
        BigDecimal serviceCost = calculateServiceCost(
            bookingData.getParcelWeightInGram(),
            bookingData.getParcelDeliveryType(),
            bookingData.getParcelPackingPreference(),
            isOfficerBooking
        );
        bookingData.setParcelServiceCost(serviceCost);
        
        return bookingRepository.save(bookingData);
    }

    public BigDecimal calculateServiceCost(Integer weightInGram, DeliveryType deliveryType, 
                                         PackingPreference packingPreference, boolean isOfficerBooking) {
        double cost = BASE_RATE;
        cost += WEIGHT_CHARGE_PER_GRAM * weightInGram;
        cost += deliveryType.getCost();
        cost += packingPreference.getCost();
        
        if (isOfficerBooking) {
            cost += ADMIN_FEE;
        }
        
        cost = cost * (1 + TAX_RATE);
        
        return BigDecimal.valueOf(cost).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public List<Booking> getBookingsByCustomer(String customerId) {
        Optional<User> userOpt = userRepository.findByCustomerId(customerId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return bookingRepository.findByCustomerOrderByCreatedAtDesc(userOpt.get());
    }

    public Optional<Booking> getBookingByBookingId(String bookingId) {
        return bookingRepository.findByBookingId(bookingId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking updateBookingStatus(String bookingId, BookingStatus status) {
        Optional<Booking> bookingOpt = bookingRepository.findByBookingId(bookingId);
        if (!bookingOpt.isPresent()) {
            throw new RuntimeException("Booking not found");
        }
        
        Booking booking = bookingOpt.get();
        booking.setStatus(status);
        booking.setUpdatedAt(LocalDateTime.now());
        
        return bookingRepository.save(booking);
    }

    private String generateBookingId() {
        String bookingId;
        do {
            bookingId = "BK" + String.format("%08d", new Random().nextInt(100000000));
        } while (bookingRepository.existsByBookingId(bookingId));
        return bookingId;
    }
}