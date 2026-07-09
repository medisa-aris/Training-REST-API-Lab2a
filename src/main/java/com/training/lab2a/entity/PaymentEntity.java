package com.training.lab2a.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotBlank
    @Column(name = "currency", nullable = false)
    private String currency;

    @NotBlank
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonBackReference
    private BookingEntity booking;

    public PaymentEntity() {
    }

    public PaymentEntity(Long id, Double amount, String currency, String status, BookingEntity booking) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.booking = booking;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BookingEntity getBooking() { return booking; }
    public void setBooking(BookingEntity booking) { this.booking = booking; }
}
