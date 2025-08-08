package com.example.demo.Service;

import com.example.demo.Entity.Patient;
import com.example.demo.Repository.PatientRepository;
import com.example.demo.Request.PatientRegistrationRequest;
import com.example.demo.Response.PatientRegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public PatientRegistrationResponse registerPatient(PatientRegistrationRequest request) {
        Map<String, String[]> validationErrors = validateRegistrationRequest(request);
        
        if (!validationErrors.isEmpty()) {
            return new PatientRegistrationResponse(
                false, 
                "Validation failed", 
                null
            );
        }

        // Check for existing email and phone
        if (patientRepository.existsByEmail(request.getEmail())) {
            Map<String, String[]> errors = new HashMap<>();
            errors.put("email", new String[]{"Email is already registered"});
            return new PatientRegistrationResponse(false, "Validation failed", null);
        }

        if (patientRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            Map<String, String[]> errors = new HashMap<>();
            errors.put("phone_number", new String[]{"Phone number is already registered"});
            return new PatientRegistrationResponse(false, "Validation failed", null);
        }

        // Create patient entity
        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setAddress(request.getAddress());
        patient.setEmergencyContact(request.getEmergencyContact());
        patient.setMedicalHistory(request.getMedicalHistory());
        patient.setInsuranceInfo(request.getInsuranceInfo());
        // emailVerified, phoneVerified, isActive, createdAt, updatedAt are set by default

        Patient savedPatient = patientRepository.save(patient);

        // Create response
        PatientRegistrationResponse.PatientData data = new PatientRegistrationResponse.PatientData(
            savedPatient.getId(),
            savedPatient.getEmail(),
            savedPatient.getPhoneNumber(),
            savedPatient.isEmailVerified(),
            savedPatient.isPhoneVerified()
        );

        return new PatientRegistrationResponse(
            true,
            "Patient registered successfully. Verification email sent.",
            data
        );
    }

    private Map<String, String[]> validateRegistrationRequest(PatientRegistrationRequest request) {
        Map<String, String[]> errors = new HashMap<>();

        // Password validation
        if (request.getPassword() == null || request.getPassword().length() < 8) {
            errors.put("password", new String[]{"Password must contain at least 8 characters"});
        }

        // Age validation (COPPA compliance)
        if (request.getDateOfBirth() != null) {
            LocalDate thirteenYearsAgo = LocalDate.now().minusYears(13);
            if (request.getDateOfBirth().isAfter(thirteenYearsAgo)) {
                errors.put("date_of_birth", new String[]{"Must be at least 13 years old"});
            }
        }

        // Password confirmation
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            errors.put("confirm_password", new String[]{"Passwords must match"});
        }

        return errors;
    }
} 