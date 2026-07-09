package com.training.lab2a.repository;

import com.training.lab2a.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    List<FlightEntity> findByOriginCodeAndDestinationCodeAndDepartureBetween(String originCode, String destinationCode, LocalDateTime start, LocalDateTime end);
    Optional<FlightEntity> findByFlightNumber(String flightNumber);
}
