package com.example.demo.Entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Embeddable
@Data
public class AvailabilityPricing {
    @DecimalMin("0.0")
    private BigDecimal baseFee;

    private boolean insuranceAccepted = false;

    @NotNull
    private String currency = "USD";
} 