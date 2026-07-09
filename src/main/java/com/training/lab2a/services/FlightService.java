package com.training.lab2a.services;

import com.training.lab2a.entity.FlightEntity;
import com.training.lab2a.exception.ResourceNotFoundException;
import com.training.lab2a.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<FlightEntity> searchFlights(String origin, String destination, String departureDate) {
        LocalDateTime start = LocalDateTime.parse(departureDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(departureDate + "T23:59:59");
        return flightRepository.findByOriginCodeAndDestinationCodeAndDepartureBetween(origin.toUpperCase(), destination.toUpperCase(), start, end);
    }

    public FlightEntity getFlightById(String flightId) {
        Long id = Long.parseLong(flightId.replace("FL-", ""));
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + flightId));
    }

    public FlightEntity saveFlight(FlightEntity flight) {
        return flightRepository.save(flight);
    }
}
