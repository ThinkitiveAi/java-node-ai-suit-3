package com.example.demo.Repository;

import com.example.demo.Entity.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, UUID> {
    
    List<AppointmentSlot> findByProviderId(UUID providerId);
    
    List<AppointmentSlot> findByPatientId(UUID patientId);
    
    List<AppointmentSlot> findByProviderIdAndSlotStartTimeBetween(UUID providerId, LocalDateTime startTime, LocalDateTime endTime);
    
    List<AppointmentSlot> findByProviderIdAndStatus(UUID providerId, AppointmentSlot.SlotStatus status);
    
    Optional<AppointmentSlot> findByBookingReference(String bookingReference);
    
    @Query("SELECT as FROM AppointmentSlot as WHERE as.provider.id = :providerId AND as.slotStartTime >= :startTime AND as.status = 'AVAILABLE'")
    List<AppointmentSlot> findAvailableSlotsByProvider(@Param("providerId") UUID providerId, @Param("startTime") LocalDateTime startTime);
    
    @Query("SELECT COUNT(as) FROM AppointmentSlot as WHERE as.availability.id = :availabilityId AND as.status = 'BOOKED'")
    long countBookedSlotsByAvailability(@Param("availabilityId") UUID availabilityId);
} 