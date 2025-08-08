package com.example.demo.Service;

import com.example.demo.Entity.*;
import com.example.demo.Repository.AppointmentSlotRepository;
import com.example.demo.Repository.ProviderAvailabilityRepository;
import com.example.demo.Repository.ProviderRepository;
import com.example.demo.Request.ProviderAvailabilityRequest;
import com.example.demo.Response.ProviderAvailabilityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProviderAvailabilityService {
    private final ProviderAvailabilityRepository availabilityRepository;
    private final AppointmentSlotRepository slotRepository;
    private final ProviderRepository providerRepository;

    @Transactional
    public ProviderAvailabilityResponse createAvailability(UUID providerId, ProviderAvailabilityRequest request) {
        // Validate provider exists
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Provider not found"));

        // Check for overlapping availability
        List<ProviderAvailability> overlapping = availabilityRepository.findOverlappingAvailability(
                providerId, request.getDate(), request.getStartTime());
        
        if (!overlapping.isEmpty()) {
            throw new IllegalArgumentException("Availability overlaps with existing schedule");
        }

        // Create availability entries
        List<ProviderAvailability> availabilities = new ArrayList<>();
        List<AppointmentSlot> slots = new ArrayList<>();

        if (request.isRecurring()) {
            availabilities = createRecurringAvailability(provider, request);
        } else {
            availabilities = createSingleAvailability(provider, request);
        }

        // Generate appointment slots for each availability
        for (ProviderAvailability availability : availabilities) {
            slots.addAll(generateAppointmentSlots(availability));
        }

        // Save all entities
        availabilityRepository.saveAll(availabilities);
        slotRepository.saveAll(slots);

        // Calculate response data
        int totalSlots = slots.size();
        LocalDate endDate = request.isRecurring() ? request.getRecurrenceEndDate() : request.getDate();

        ProviderAvailabilityResponse.AvailabilityData data = new ProviderAvailabilityResponse.AvailabilityData(
                availabilities.get(0).getId(),
                totalSlots,
                new ProviderAvailabilityResponse.DateRange(request.getDate(), endDate),
                totalSlots * request.getMaxAppointmentsPerSlot()
        );

        return new ProviderAvailabilityResponse(true, "Availability slots created successfully", data);
    }

    private List<ProviderAvailability> createSingleAvailability(Provider provider, ProviderAvailabilityRequest request) {
        ProviderAvailability availability = new ProviderAvailability();
        availability.setProvider(provider);
        availability.setDate(request.getDate());
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());
        availability.setTimezone(request.getTimezone());
        availability.setSlotDuration(request.getSlotDuration());
        availability.setBreakDuration(request.getBreakDuration());
        availability.setMaxAppointmentsPerSlot(request.getMaxAppointmentsPerSlot());
        availability.setAppointmentType(request.getAppointmentType());
        availability.setLocation(request.getLocation());
        availability.setPricing(request.getPricing());
        availability.setNotes(request.getNotes());
        availability.setSpecialRequirements(request.getSpecialRequirements());

        return List.of(availability);
    }

    private List<ProviderAvailability> createRecurringAvailability(Provider provider, ProviderAvailabilityRequest request) {
        List<ProviderAvailability> availabilities = new ArrayList<>();
        LocalDate currentDate = request.getDate();
        LocalDate endDate = request.getRecurrenceEndDate();

        while (!currentDate.isAfter(endDate)) {
            ProviderAvailability availability = new ProviderAvailability();
            availability.setProvider(provider);
            availability.setDate(currentDate);
            availability.setStartTime(request.getStartTime());
            availability.setEndTime(request.getEndTime());
            availability.setTimezone(request.getTimezone());
            availability.setRecurring(true);
            availability.setRecurrencePattern(request.getRecurrencePattern());
            availability.setRecurrenceEndDate(request.getRecurrenceEndDate());
            availability.setSlotDuration(request.getSlotDuration());
            availability.setBreakDuration(request.getBreakDuration());
            availability.setMaxAppointmentsPerSlot(request.getMaxAppointmentsPerSlot());
            availability.setAppointmentType(request.getAppointmentType());
            availability.setLocation(request.getLocation());
            availability.setPricing(request.getPricing());
            availability.setNotes(request.getNotes());
            availability.setSpecialRequirements(request.getSpecialRequirements());

            availabilities.add(availability);

            // Calculate next date based on recurrence pattern
            switch (request.getRecurrencePattern()) {
                case DAILY:
                    currentDate = currentDate.plusDays(1);
                    break;
                case WEEKLY:
                    currentDate = currentDate.plusWeeks(1);
                    break;
                case MONTHLY:
                    currentDate = currentDate.plusMonths(1);
                    break;
            }
        }

        return availabilities;
    }

    private List<AppointmentSlot> generateAppointmentSlots(ProviderAvailability availability) {
        List<AppointmentSlot> slots = new ArrayList<>();
        LocalTime currentTime = availability.getStartTime();
        LocalTime endTime = availability.getEndTime();

        while (currentTime.plusMinutes(availability.getSlotDuration()).isBefore(endTime) || 
               currentTime.plusMinutes(availability.getSlotDuration()).equals(endTime)) {
            
            AppointmentSlot slot = new AppointmentSlot();
            slot.setAvailability(availability);
            slot.setProvider(availability.getProvider());
            slot.setSlotStartTime(LocalDateTime.of(availability.getDate(), currentTime));
            slot.setSlotEndTime(LocalDateTime.of(availability.getDate(), 
                    currentTime.plusMinutes(availability.getSlotDuration())));
            slot.setAppointmentType(availability.getAppointmentType().toString());
            slot.setBookingReference(generateBookingReference());

            slots.add(slot);

            // Move to next slot (including break duration)
            currentTime = currentTime.plusMinutes(availability.getSlotDuration() + availability.getBreakDuration());
        }

        return slots;
    }

    private String generateBookingReference() {
        return "REF-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
} 