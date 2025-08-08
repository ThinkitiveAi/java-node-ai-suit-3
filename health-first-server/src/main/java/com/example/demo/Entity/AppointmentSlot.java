package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointment_slots", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"booking_reference"})
})
@Data
public class AppointmentSlot {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "availability_id", nullable = false)
    @NotNull
    private ProviderAvailability availability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    @NotNull
    private Provider provider;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime slotStartTime;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime slotEndTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status = SlotStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(nullable = false)
    @NotNull
    private String appointmentType;

    @Column(unique = true, nullable = false)
    @NotNull
    private String bookingReference;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum SlotStatus {
        AVAILABLE, BOOKED, CANCELLED, BLOCKED
    }
} 