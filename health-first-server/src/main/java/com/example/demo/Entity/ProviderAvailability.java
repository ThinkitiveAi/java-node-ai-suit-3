package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "provider_availability")
@Data
public class ProviderAvailability {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    @NotNull
    private Provider provider;

    @Column(nullable = false)
    @NotNull
    private LocalDate date;

    @Column(nullable = false)
    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Column(nullable = false)
    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Column(nullable = false, length = 50)
    @NotBlank
    private String timezone;

    @Column(nullable = false)
    private boolean recurring = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RecurrencePattern recurrencePattern;

    @Column
    private LocalDate recurrenceEndDate;

    @Column(nullable = false)
    @Min(15)
    @Max(240)
    private int slotDuration = 30;

    @Column(nullable = false)
    @Min(0)
    @Max(120)
    private int breakDuration = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityStatus status = AvailabilityStatus.AVAILABLE;

    @Column(nullable = false)
    @Min(1)
    @Max(10)
    private int maxAppointmentsPerSlot = 1;

    @Column(nullable = false)
    @Min(0)
    private int currentAppointments = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentType appointmentType = AppointmentType.CONSULTATION;

    @Embedded
    @NotNull
    private AvailabilityLocation location;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "baseFee", column = @Column(name = "pricing_base_fee")),
        @AttributeOverride(name = "insuranceAccepted", column = @Column(name = "pricing_insurance_accepted")),
        @AttributeOverride(name = "currency", column = @Column(name = "pricing_currency"))
    })
    private AvailabilityPricing pricing;

    @Column(length = 500)
    @Size(max = 500)
    private String notes;

    @ElementCollection
    @CollectionTable(name = "provider_availability_special_requirements", joinColumns = @JoinColumn(name = "availability_id"))
    @Column(name = "requirement")
    private List<String> specialRequirements;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum RecurrencePattern {
        DAILY, WEEKLY, MONTHLY
    }

    public enum AvailabilityStatus {
        AVAILABLE, BOOKED, CANCELLED, BLOCKED, MAINTENANCE
    }

    public enum AppointmentType {
        CONSULTATION, FOLLOW_UP, EMERGENCY, TELEMEDICINE
    }
} 