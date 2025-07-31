package com.parceldelivery.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @NotBlank
    @Column(name = "receiver_name")
    private String receiverName;

    @NotBlank
    @Column(name = "receiver_address")
    private String receiverAddress;

    @NotBlank
    @Column(name = "receiver_pin")
    private String receiverPin;

    @NotBlank
    @Column(name = "receiver_mobile")
    private String receiverMobile;

    @NotNull
    @Positive
    @Column(name = "parcel_weight_gram")
    private Integer parcelWeightInGram;

    @NotBlank
    @Column(name = "parcel_contents_description")
    private String parcelContentsDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "parcel_delivery_type")
    private DeliveryType parcelDeliveryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "parcel_packing_preference")
    private PackingPreference parcelPackingPreference;

    @Column(name = "parcel_pickup_time")
    private LocalDateTime parcelPickupTime;

    @Column(name = "parcel_dropoff_time")
    private LocalDateTime parcelDropoffTime;

    @NotNull
    @Column(name = "parcel_service_cost", precision = 10, scale = 2)
    private BigDecimal parcelServiceCost;

    @Column(name = "parcel_payment_time")
    private LocalDateTime parcelPaymentTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.ASSIGNED;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public Booking() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getReceiverAddress() { return receiverAddress; }
    public void setReceiverAddress(String receiverAddress) { this.receiverAddress = receiverAddress; }

    public String getReceiverPin() { return receiverPin; }
    public void setReceiverPin(String receiverPin) { this.receiverPin = receiverPin; }

    public String getReceiverMobile() { return receiverMobile; }
    public void setReceiverMobile(String receiverMobile) { this.receiverMobile = receiverMobile; }

    public Integer getParcelWeightInGram() { return parcelWeightInGram; }
    public void setParcelWeightInGram(Integer parcelWeightInGram) { this.parcelWeightInGram = parcelWeightInGram; }

    public String getParcelContentsDescription() { return parcelContentsDescription; }
    public void setParcelContentsDescription(String parcelContentsDescription) { this.parcelContentsDescription = parcelContentsDescription; }

    public DeliveryType getParcelDeliveryType() { return parcelDeliveryType; }
    public void setParcelDeliveryType(DeliveryType parcelDeliveryType) { this.parcelDeliveryType = parcelDeliveryType; }

    public PackingPreference getParcelPackingPreference() { return parcelPackingPreference; }
    public void setParcelPackingPreference(PackingPreference parcelPackingPreference) { this.parcelPackingPreference = parcelPackingPreference; }

    public LocalDateTime getParcelPickupTime() { return parcelPickupTime; }
    public void setParcelPickupTime(LocalDateTime parcelPickupTime) { this.parcelPickupTime = parcelPickupTime; }

    public LocalDateTime getParcelDropoffTime() { return parcelDropoffTime; }
    public void setParcelDropoffTime(LocalDateTime parcelDropoffTime) { this.parcelDropoffTime = parcelDropoffTime; }

    public BigDecimal getParcelServiceCost() { return parcelServiceCost; }
    public void setParcelServiceCost(BigDecimal parcelServiceCost) { this.parcelServiceCost = parcelServiceCost; }

    public LocalDateTime getParcelPaymentTime() { return parcelPaymentTime; }
    public void setParcelPaymentTime(LocalDateTime parcelPaymentTime) { this.parcelPaymentTime = parcelPaymentTime; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}