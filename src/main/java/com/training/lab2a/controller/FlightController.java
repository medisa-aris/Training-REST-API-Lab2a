package com.training.lab2a.controller;

import com.training.lab2a.entity.FlightEntity;
import com.training.lab2a.exception.ResourceNotFoundException;
import com.training.lab2a.services.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@Tag(name = "Flights", description = "Flight search and details")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights")
    @Operation(summary = "Search flights", description = "Search flights by origin, destination and departure date")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flights found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FlightEntity.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<List<FlightEntity>> searchFlights(
            @Parameter(description = "Origin airport code", required = true, example = "JFK") @RequestParam String origin,
            @Parameter(description = "Destination airport code", required = true, example = "LAX") @RequestParam String destination,
            @Parameter(description = "Departure date", required = true, example = "2026-07-20") @RequestParam String departureDate) {

        return ResponseEntity.ok(flightService.searchFlights(origin, destination, departureDate));
    }

    @GetMapping("/flights/{flightId}")
    @Operation(summary = "Get flight details", description = "Fetch a single flight by its identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Flight found", content = @Content(schema = @Schema(implementation = FlightEntity.class))),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    public ResponseEntity<FlightEntity> getFlight(
            @Parameter(description = "Flight identifier", required = true, example = "FL-101") @PathVariable String flightId) {
        return ResponseEntity.ok(flightService.getFlightById(flightId));
    }
}
