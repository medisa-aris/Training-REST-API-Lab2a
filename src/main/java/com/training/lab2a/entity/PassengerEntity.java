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

@Entity
@Table(name = "passengers")
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "seat", nullable = false)
    private String seat;

    @NotBlank
    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonBackReference
    private BookingEntity booking;

    public PassengerEntity() {
    }

    public PassengerEntity(Long id, String name, String seat, String type, BookingEntity booking) {
        this.id = id;
        this.name = name;
        this.seat = seat;
        this.type = type;
        this.booking = booking;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSeat() { return seat; }
    public void setSeat(String seat) { this.seat = seat; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BookingEntity getBooking() { return booking; }
    public void setBooking(BookingEntity booking) { this.booking = booking; }
}
