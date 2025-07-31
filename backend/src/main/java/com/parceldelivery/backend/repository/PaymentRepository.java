package com.parceldelivery.backend.repository;

import com.parceldelivery.backend.model.Payment;
import com.parceldelivery.backend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(String paymentId);
    Optional<Payment> findByTransactionId(String transactionId);
    Optional<Payment> findByBooking(Booking booking);
    Boolean existsByPaymentId(String paymentId);
    Boolean existsByTransactionId(String transactionId);
}