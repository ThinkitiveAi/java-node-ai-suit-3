package com.example.demo.Repository;

import com.example.demo.Entity.ProviderAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface ProviderAvailabilityRepository extends JpaRepository<ProviderAvailability, UUID> {
    
    List<ProviderAvailability> findByProviderId(UUID providerId);
    
    List<ProviderAvailability> findByProviderIdAndDateBetween(UUID providerId, LocalDate startDate, LocalDate endDate);
    
    List<ProviderAvailability> findByProviderIdAndStatus(UUID providerId, ProviderAvailability.AvailabilityStatus status);
    
    @Query("SELECT pa FROM ProviderAvailability pa WHERE pa.provider.id = :providerId AND pa.date >= :startDate AND pa.status = 'AVAILABLE'")
    List<ProviderAvailability> findAvailableSlotsByProvider(@Param("providerId") UUID providerId, @Param("startDate") LocalDate startDate);
    
    List<ProviderAvailability> findByProviderIdAndRecurringTrue(UUID providerId);
    
    @Query("SELECT pa FROM ProviderAvailability pa WHERE pa.provider.id = :providerId AND pa.date = :date AND pa.startTime <= :time AND pa.endTime >= :time")
    List<ProviderAvailability> findOverlappingAvailability(@Param("providerId") UUID providerId, @Param("date") LocalDate date, @Param("time") LocalTime time);
} 