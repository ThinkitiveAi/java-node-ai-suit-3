package com.example.demo.Entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Embeddable
@Data
public class AvailabilityLocation {
    @NotNull
    private LocationType type;

    private String address;

    private String roomNumber;

    public enum LocationType {
        CLINIC, HOSPITAL, TELEMEDICINE, HOME_VISIT
    }
} 