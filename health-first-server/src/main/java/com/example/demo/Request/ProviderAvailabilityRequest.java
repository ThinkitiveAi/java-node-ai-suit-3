package com.example.demo.Request;

import com.example.demo.Entity.AvailabilityLocation;
import com.example.demo.Entity.AvailabilityPricing;
import com.example.demo.Entity.ProviderAvailability;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class ProviderAvailabilityRequest {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @NotBlank
    private String timezone;

    @Min(15)
    @Max(240)
    private int slotDuration = 30;

    @Min(0)
    @Max(120)
    private int breakDuration = 0;

    private boolean recurring = false;

    private ProviderAvailability.RecurrencePattern recurrencePattern;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate recurrenceEndDate;

    @Min(1)
    @Max(10)
    private int maxAppointmentsPerSlot = 1;

    private ProviderAvailability.AppointmentType appointmentType = ProviderAvailability.AppointmentType.CONSULTATION;

    @NotNull
    private AvailabilityLocation location;

    private AvailabilityPricing pricing;

    @Size(max = 500)
    private String notes;

    private List<String> specialRequirements;

    // Custom validation for time range
    @AssertTrue(message = "End time must be after start time")
    public boolean isTimeRangeValid() {
        return startTime != null && endTime != null && endTime.isAfter(startTime);
    }

    // Custom validation for recurrence
    @AssertTrue(message = "Recurrence pattern and end date are required for recurring availability")
    public boolean isRecurrenceValid() {
        if (!recurring) return true;
        return recurrencePattern != null && recurrenceEndDate != null && recurrenceEndDate.isAfter(date);
    }

    // Custom validation for slot duration
    @AssertTrue(message = "Slot duration must be less than or equal to the time range")
    public boolean isSlotDurationValid() {
        if (startTime == null || endTime == null) return true;
        long totalMinutes = java.time.Duration.between(startTime, endTime).toMinutes();
        return slotDuration <= totalMinutes;
    }
} 