package com.example.demo.Request;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;

@Embeddable
public class ClinicAddress {
    @NotBlank
    @Size(max = 200)
    private String street;

    @NotBlank
    @Size(max = 100)
    private String city;

    @NotBlank
    @Size(max = 50)
    private String state;

    @NotBlank
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Invalid postal code")
    private String zip;

    // Getters and setters omitted for brevity
} 