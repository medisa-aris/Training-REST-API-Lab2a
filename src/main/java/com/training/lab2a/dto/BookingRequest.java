package com.training.lab2a.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BookingRequest {

    @NotBlank
    private String status;

    @NotNull
    @Valid
    private FlightRequest flight;

    @NotEmpty
    @Valid
    private List<PassengerRequest> passengers;

    @NotNull
    @Valid
    private PaymentRequest payment;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public FlightRequest getFlight() { return flight; }
    public void setFlight(FlightRequest flight) { this.flight = flight; }
    public List<PassengerRequest> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerRequest> passengers) { this.passengers = passengers; }
    public PaymentRequest getPayment() { return payment; }
    public void setPayment(PaymentRequest payment) { this.payment = payment; }

    public static class FlightRequest {
        @NotBlank
        private String flightNumber;

        @NotNull
        @Valid
        private AirportRequest origin;

        @NotNull
        @Valid
        private AirportRequest destination;

        @NotNull
        private String departure;

        @NotNull
        private String arrival;

        @NotBlank
        private String status;

        private int seatsAvailable;

        @NotNull
        private Double price;

        public String getFlightNumber() { return flightNumber; }
        public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
        public AirportRequest getOrigin() { return origin; }
        public void setOrigin(AirportRequest origin) { this.origin = origin; }
        public AirportRequest getDestination() { return destination; }
        public void setDestination(AirportRequest destination) { this.destination = destination; }
        public String getDeparture() { return departure; }
        public void setDeparture(String departure) { this.departure = departure; }
        public String getArrival() { return arrival; }
        public void setArrival(String arrival) { this.arrival = arrival; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public int getSeatsAvailable() { return seatsAvailable; }
        public void setSeatsAvailable(int seatsAvailable) { this.seatsAvailable = seatsAvailable; }
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
    }

    public static class AirportRequest {
        @NotBlank
        private String code;

        @NotBlank
        private String name;

        @NotBlank
        private String city;

        @NotBlank
        private String timezone;

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getTimezone() { return timezone; }
        public void setTimezone(String timezone) { this.timezone = timezone; }
    }

    public static class PassengerRequest {
        @NotBlank
        private String name;

        @NotBlank
        private String seat;

        @NotBlank
        private String type;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getSeat() { return seat; }
        public void setSeat(String seat) { this.seat = seat; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public static class PaymentRequest {
        @NotNull
        private Double amount;

        @NotBlank
        private String currency;

        @NotBlank
        private String status;

        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
