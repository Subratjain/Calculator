package com.parceldelivery.backend.repository;

import com.parceldelivery.backend.model.Booking;
import com.parceldelivery.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingId(String bookingId);
    List<Booking> findByCustomer(User customer);
    List<Booking> findByCustomerOrderByCreatedAtDesc(User customer);
    Boolean existsByBookingId(String bookingId);
}