package com.training.lab2a.controller;

import com.training.lab2a.dto.BookingRequest;
import com.training.lab2a.entity.BookingEntity;
import com.training.lab2a.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1")
@Tag(name = "Bookings", description = "Booking creation and management")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookings")
    @Operation(summary = "Create booking", description = "Create a new booking from the request body")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Booking created", content = @Content(schema = @Schema(implementation = BookingEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid booking payload")
    })
    public ResponseEntity<BookingEntity> createBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(bookingRequest));
    }

    @GetMapping("/bookings/{bookingId}")
    @Operation(summary = "Get booking", description = "Fetch a booking by its identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking found", content = @Content(schema = @Schema(implementation = BookingEntity.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<BookingEntity> getBooking(
            @Parameter(description = "Booking identifier", required = true, example = "BK-1") @PathVariable String bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @PatchMapping(value = "/bookings/{bookingId}", consumes = "application/merge-patch+json")
    @Operation(summary = "Update booking", description = "Partially update a booking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking updated", content = @Content(schema = @Schema(implementation = BookingEntity.class))),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "409", description = "Conflict")
    })
    public ResponseEntity<BookingEntity> updateBooking(
            @Parameter(description = "Booking identifier", required = true, example = "BK-1") @PathVariable String bookingId,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(bookingService.updateBooking(bookingId, updates));
    }

    @PostMapping("/bookings/{id}/cancellation")
    @Operation(summary = "Cancel booking", description = "Cancel an existing booking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking cancelled"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public ResponseEntity<Void> cancelBooking(
            @Parameter(description = "Booking identifier", required = true, example = "BK-1") @PathVariable String id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok().build();
    }
}
