package com.example.demo.Controller;

import com.example.demo.Request.ProviderAvailabilityRequest;
import com.example.demo.Response.ProviderAvailabilityResponse;
import com.example.demo.Service.ProviderAvailabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/provider/availability")
@RequiredArgsConstructor
public class ProviderAvailabilityController {
    private final ProviderAvailabilityService availabilityService;

    @PostMapping
    public ResponseEntity<ProviderAvailabilityResponse> createAvailability(
            @RequestParam UUID providerId,
            @Valid @RequestBody ProviderAvailabilityRequest request) {
        try {
            ProviderAvailabilityResponse response = availabilityService.createAvailability(providerId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                new ProviderAvailabilityResponse(false, e.getMessage(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ProviderAvailabilityResponse(false, "Failed to create availability", null)
            );
        }
    }
} 