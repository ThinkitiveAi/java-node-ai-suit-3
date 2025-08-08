package com.example.demo.Request;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.EmergencyContact;
import com.example.demo.Entity.InsuranceInfo;
import com.example.demo.Entity.Patient;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatientRegistrationRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
             message = "Password must contain at least 8 characters, one uppercase, one lowercase, one number, and one special character")
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotNull
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotNull
    private Patient.Gender gender;

    @NotNull
    private Address address;

    private EmergencyContact emergencyContact;

    private List<String> medicalHistory;

    private InsuranceInfo insuranceInfo;

    // Custom validation for password confirmation
    @AssertTrue(message = "Passwords must match")
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }

    // Custom validation for age (COPPA compliance)
    @AssertTrue(message = "Must be at least 13 years old")
    public boolean isAgeValid() {
        if (dateOfBirth == null) return false;
        LocalDate thirteenYearsAgo = LocalDate.now().minusYears(13);
        return dateOfBirth.isBefore(thirteenYearsAgo) || dateOfBirth.isEqual(thirteenYearsAgo);
    }
} 