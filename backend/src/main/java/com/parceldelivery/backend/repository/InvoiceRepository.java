package com.parceldelivery.backend.repository;

import com.parceldelivery.backend.model.Invoice;
import com.parceldelivery.backend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    Optional<Invoice> findByBooking(Booking booking);
    Boolean existsByInvoiceNumber(String invoiceNumber);
}