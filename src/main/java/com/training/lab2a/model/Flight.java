package com.training.lab2a.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

@Schema(name = "Flight", description = "Flight search result and details")
public class Flight {

    @Schema(example = "FL-101")
    @NotBlank
    private String flightNumber;

    @Schema(description = "Origin airport")
    @NotNull
    @Valid
    private Airport origin;

    @Schema(description = "Destination airport")
    @NotNull
    @Valid
    private Airport destination;

    @Schema(example = "2026-07-20T10:30:00")
    @NotNull
    private LocalDateTime departure;

    @Schema(example = "2026-07-20T13:45:00")
    @NotNull
    private LocalDateTime arrival;

    @Schema(example = "SCHEDULED")
    @NotBlank
    private String status;

    @Schema(example = "12")
    @PositiveOrZero
    private int seatsAvailable;

    @Schema(example = "320.5")
    @NotNull
    private Double price;

    public Flight() {
    }

    public Flight(String flightNumber, Airport origin, Airport destination, LocalDateTime departure, LocalDateTime arrival,
                  String status, int seatsAvailable, Double price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.status = status;
        this.seatsAvailable = seatsAvailable;
        this.price = price;
    }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public Airport getOrigin() { return origin; }
    public void setOrigin(Airport origin) { this.origin = origin; }
    public Airport getDestination() { return destination; }
    public void setDestination(Airport destination) { this.destination = destination; }
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
