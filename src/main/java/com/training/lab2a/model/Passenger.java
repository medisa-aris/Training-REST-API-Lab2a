package com.training.lab2a.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Passenger", description = "Passenger details inside a booking")
public class Passenger {

    @Schema(example = "Jane Doe")
    @NotBlank
    private String name;

    @Schema(example = "12A")
    @NotBlank
    private String seat;

    @Schema(example = "ADULT")
    @NotBlank
    private String type;

    public Passenger() {
    }

    public Passenger(String name, String seat, String type) {
        this.name = name;
        this.seat = seat;
        this.type = type;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSeat() { return seat; }
    public void setSeat(String seat) { this.seat = seat; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
