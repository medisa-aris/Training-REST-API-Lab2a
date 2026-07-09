package com.training.lab2a.services;

import com.training.lab2a.dto.BookingRequest;
import com.training.lab2a.entity.BookingEntity;
import com.training.lab2a.entity.FlightEntity;
import com.training.lab2a.entity.PassengerEntity;
import com.training.lab2a.entity.PaymentEntity;
import com.training.lab2a.exception.ResourceNotFoundException;
import com.training.lab2a.repository.BookingRepository;
import com.training.lab2a.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    public BookingService(BookingRepository bookingRepository, FlightRepository flightRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
    }

    public BookingEntity createBooking(BookingRequest bookingRequest) {
        FlightEntity flight = resolveFlight(bookingRequest.getFlight());
        BookingEntity booking = new BookingEntity();
        booking.setStatus(bookingRequest.getStatus());
        booking.setFlight(flight);

        List<PassengerEntity> passengers = new ArrayList<>();
        if (bookingRequest.getPassengers() != null) {
            for (BookingRequest.PassengerRequest passengerRequest : bookingRequest.getPassengers()) {
                PassengerEntity passenger = new PassengerEntity();
                passenger.setName(passengerRequest.getName());
                passenger.setSeat(passengerRequest.getSeat());
                passenger.setType(passengerRequest.getType());
                passenger.setBooking(booking);
                passengers.add(passenger);
            }
        }
        booking.setPassengers(passengers);

        List<PaymentEntity> payments = new ArrayList<>();
        if (bookingRequest.getPayment() != null) {
            PaymentEntity payment = new PaymentEntity();
            payment.setAmount(bookingRequest.getPayment().getAmount());
            payment.setCurrency(bookingRequest.getPayment().getCurrency());
            payment.setStatus(bookingRequest.getPayment().getStatus());
            payment.setBooking(booking);
            payments.add(payment);
        }
        booking.setPayments(payments);

        return bookingRepository.save(booking);
    }

    public BookingEntity getBookingById(String bookingId) {
        Long id = Long.parseLong(bookingId.replace("BK-", ""));
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
    }

    public BookingEntity updateBooking(String bookingId, Map<String, Object> updates) {
        BookingEntity booking = getBookingById(bookingId);
        if (updates.containsKey("status")) {
            booking.setStatus((String) updates.get("status"));
        }
        return bookingRepository.save(booking);
    }

    public BookingEntity cancelBooking(String bookingId) {
        BookingEntity booking = getBookingById(bookingId);
        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }

    private FlightEntity resolveFlight(BookingRequest.FlightRequest flightRequest) {
        FlightEntity flight = flightRepository.findByFlightNumber(flightRequest.getFlightNumber()).orElse(null);
        if (flight != null) {
            return flight;
        }

        FlightEntity newFlight = new FlightEntity();
        newFlight.setFlightNumber(flightRequest.getFlightNumber());
        newFlight.setOriginCode(flightRequest.getOrigin().getCode());
        newFlight.setOriginName(flightRequest.getOrigin().getName());
        newFlight.setOriginCity(flightRequest.getOrigin().getCity());
        newFlight.setOriginTimezone(flightRequest.getOrigin().getTimezone());
        newFlight.setDestinationCode(flightRequest.getDestination().getCode());
        newFlight.setDestinationName(flightRequest.getDestination().getName());
        newFlight.setDestinationCity(flightRequest.getDestination().getCity());
        newFlight.setDestinationTimezone(flightRequest.getDestination().getTimezone());
        newFlight.setDeparture(LocalDateTime.parse(flightRequest.getDeparture()));
        newFlight.setArrival(LocalDateTime.parse(flightRequest.getArrival()));
        newFlight.setStatus(flightRequest.getStatus());
        newFlight.setSeatsAvailable(flightRequest.getSeatsAvailable());
        newFlight.setPrice(flightRequest.getPrice());
        return flightRepository.save(newFlight);
    }
}
