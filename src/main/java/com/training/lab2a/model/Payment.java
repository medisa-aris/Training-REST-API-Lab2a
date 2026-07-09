package com.training.lab2a.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Payment", description = "Payment information for a booking")
public class Payment {

    @Schema(example = "320.5")
    @NotNull
    private Double amount;

    @Schema(example = "USD")
    @NotBlank
    private String currency;

    @Schema(example = "PENDING")
    @NotBlank
    private String status;

    public Payment() {
    }

    public Payment(Double amount, String currency, String status) {
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
