package com.example.demo.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderAvailabilityResponse {
    private boolean success;
    private String message;
    private AvailabilityData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AvailabilityData {
        private UUID availabilityId;
        private int slotsCreated;
        private DateRange dateRange;
        private int totalAppointmentsAvailable;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DateRange {
        private LocalDate start;
        private LocalDate end;
    }
} 