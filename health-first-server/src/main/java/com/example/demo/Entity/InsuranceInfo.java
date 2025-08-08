package com.example.demo.Entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Embeddable
@Data
public class InsuranceInfo {
    @Size(max = 100)
    private String provider;

    @Size(max = 50)
    private String policyNumber;
} 