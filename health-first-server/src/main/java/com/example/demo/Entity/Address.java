package com.example.demo.Entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Embeddable
@Data
public class Address {
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
} 