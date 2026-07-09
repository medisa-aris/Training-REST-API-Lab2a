package com.training.lab2a.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "flight_number", nullable = false, unique = true)
    private String flightNumber;

    @NotBlank
    @Column(name = "origin_code", nullable = false)
    private String originCode;

    @NotBlank
    @Column(name = "origin_name", nullable = false)
    private String originName;

    @NotBlank
    @Column(name = "origin_city", nullable = false)
    private String originCity;

    @NotBlank
    @Column(name = "origin_timezone", nullable = false)
    private String originTimezone;

    @NotBlank
    @Column(name = "destination_code", nullable = false)
    private String destinationCode;

    @NotBlank
    @Column(name = "destination_name", nullable = false)
    private String destinationName;

    @NotBlank
    @Column(name = "destination_city", nullable = false)
    private String destinationCity;

    @NotBlank
    @Column(name = "destination_timezone", nullable = false)
    private String destinationTimezone;

    @NotNull
    @Column(name = "departure", nullable = false)
    private LocalDateTime departure;

    @NotNull
    @Column(name = "arrival", nullable = false)
    private LocalDateTime arrival;

    @NotBlank
    @Column(name = "status", nullable = false)
    private String status;

    @PositiveOrZero
    @Column(name = "seats_available", nullable = false)
    private int seatsAvailable;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    public FlightEntity() {
    }

    public FlightEntity(Long id, String flightNumber, String originCode, String originName, String originCity, String originTimezone,
                        String destinationCode, String destinationName, String destinationCity, String destinationTimezone,
                        LocalDateTime departure, LocalDateTime arrival, String status, int seatsAvailable, Double price) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.originCode = originCode;
        this.originName = originName;
        this.originCity = originCity;
        this.originTimezone = originTimezone;
        this.destinationCode = destinationCode;
        this.destinationName = destinationName;
        this.destinationCity = destinationCity;
        this.destinationTimezone = destinationTimezone;
        this.departure = departure;
        this.arrival = arrival;
        this.status = status;
        this.seatsAvailable = seatsAvailable;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public String getOriginCode() { return originCode; }
    public void setOriginCode(String originCode) { this.originCode = originCode; }
    public String getOriginName() { return originName; }
    public void setOriginName(String originName) { this.originName = originName; }
    public String getOriginCity() { return originCity; }
    public void setOriginCity(String originCity) { this.originCity = originCity; }
    public String getOriginTimezone() { return originTimezone; }
    public void setOriginTimezone(String originTimezone) { this.originTimezone = originTimezone; }
    public String getDestinationCode() { return destinationCode; }
    public void setDestinationCode(String destinationCode) { this.destinationCode = destinationCode; }
    public String getDestinationName() { return destinationName; }
    public void setDestinationName(String destinationName) { this.destinationName = destinationName; }
    public String getDestinationCity() { return destinationCity; }
    public void setDestinationCity(String destinationCity) { this.destinationCity = destinationCity; }
    public String getDestinationTimezone() { return destinationTimezone; }
    public void setDestinationTimezone(String destinationTimezone) { this.destinationTimezone = destinationTimezone; }
    public LocalDateTime getDeparture() { return departure; }
    public void setDeparture(LocalDateTime departure) { this.departure = departure; }
    public LocalDateTime getArrival() { return arrival; }
    public void setArrival(LocalDateTime arrival) { this.arrival = arrival; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getSeatsAvailable() { return seatsAvailable; }
    public void setSeatsAvailable(int seatsAvailable) { this.seatsAvailable = seatsAvailable; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
