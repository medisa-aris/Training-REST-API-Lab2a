package com.training.lab2a.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "Airport", description = "Airport information")
public class Airport {

    @Schema(example = "JFK")
    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$")
    private String code;

    @Schema(example = "John F. Kennedy International Airport")
    @NotBlank
    private String name;

    @Schema(example = "New York")
    @NotBlank
    private String city;

    @Schema(example = "America/New_York")
    @NotBlank
    private String timezone;

    public Airport() {
    }

    public Airport(String code, String name, String city, String timezone) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.timezone = timezone;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
}
