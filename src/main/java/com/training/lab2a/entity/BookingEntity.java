package com.training.lab2a.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "bookings")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightEntity flight;

    @NotEmpty
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PassengerEntity> passengers;

    @NotNull
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PaymentEntity> payments;

    public BookingEntity() {
    }

    public BookingEntity(Long id, String status, FlightEntity flight, List<PassengerEntity> passengers, List<PaymentEntity> payments) {
        this.id = id;
        this.status = status;
        this.flight = flight;
        this.passengers = passengers;
        this.payments = payments;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public FlightEntity getFlight() { return flight; }
    public void setFlight(FlightEntity flight) { this.flight = flight; }
    public List<PassengerEntity> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerEntity> passengers) { this.passengers = passengers; }
    public List<PaymentEntity> getPayments() { return payments; }
    public void setPayments(List<PaymentEntity> payments) { this.payments = payments; }
}
