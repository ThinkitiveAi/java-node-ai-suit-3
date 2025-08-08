package com.example.demo.Entity;

import com.example.demo.Request.ClinicAddress;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "providers", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"}),
    @UniqueConstraint(columnNames = {"phone_number"}),
    @UniqueConstraint(columnNames = {"license_number"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50)
    @NotBlank
    private String firstName;

    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50)
    @NotBlank
    private String lastName;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @Column(nullable = false)
    @NotBlank
    private String passwordHash;

    @Column(nullable = false, length = 100)
    @Size(min = 3, max = 100)
    @NotBlank
    private String specialization;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "License number must be alphanumeric")
    private String licenseNumber;

    @Min(0)
    @Max(50)
    private int yearsOfExperience;

    @Embedded
    private ClinicAddress clinicAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Getters and setters omitted for brevity

    public enum VerificationStatus {
        PENDING, VERIFIED, REJECTED
    }
} 