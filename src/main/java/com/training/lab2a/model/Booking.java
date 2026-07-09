package com.training.lab2a.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(name = "Booking", description = "Flight booking")
public class Booking {

    @Schema(description = "Booking identifier", readOnly = true, example = "BK-1001")
    private String id;

    @Schema(example = "PENDING")
    @NotBlank
    private String status;

    @Schema(description = "Booked flight")
    @NotNull
    @Valid
    private Flight flight;

    @Schema(description = "Passengers in the booking")
    @NotEmpty
    @Valid
    private List<Passenger> passengers;

    @Schema(description = "Payment details")
    @NotNull
    @Valid
    private Payment payment;

    public Booking() {
    }

    public Booking(String id, String status, Flight flight, List<Passenger> passengers, Payment payment) {
        this.id = id;
        this.status = status;
        this.flight = flight;
        this.passengers = passengers;
        this.payment = payment;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }
    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }
    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
}
