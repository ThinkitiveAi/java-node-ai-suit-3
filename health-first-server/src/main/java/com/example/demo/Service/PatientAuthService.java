package com.example.demo.Service;

import com.example.demo.Entity.Patient;
import com.example.demo.Repository.PatientRepository;
import com.example.demo.Request.PatientLoginRequest;
import com.example.demo.Response.PatientLoginResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientAuthService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret:your-very-very-very-very-very-secret-key-1234}")
    private String jwtSecret;

    @Value("${jwt.expiration:1800000}") // 30 minutes default
    private long jwtExpirationMs;

    public PatientLoginResponse authenticatePatient(PatientLoginRequest request) {
        Optional<Patient> patientOpt = patientRepository.findByEmail(request.getEmail());
        
        if (patientOpt.isEmpty()) {
            return new PatientLoginResponse(false, "Invalid email or password", null);
        }

        Patient patient = patientOpt.get();
        
        if (!passwordEncoder.matches(request.getPassword(), patient.getPasswordHash())) {
            return new PatientLoginResponse(false, "Invalid email or password", null);
        }

        if (!patient.isActive()) {
            return new PatientLoginResponse(false, "Account is deactivated", null);
        }

        String token = generateToken(patient);
        
        PatientLoginResponse.LoginData loginData = new PatientLoginResponse.LoginData(
            token,
            (int) (jwtExpirationMs / 1000), // Convert to seconds
            "Bearer",
            createPatientData(patient)
        );

        return new PatientLoginResponse(true, "Login successful", loginData);
    }

    private String generateToken(Patient patient) {
        return Jwts.builder()
                .setSubject(patient.getId().toString())
                .claim("email", patient.getEmail())
                .claim("role", "PATIENT")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();
    }

    private PatientLoginResponse.PatientData createPatientData(Patient patient) {
        return new PatientLoginResponse.PatientData(
            patient.getId().toString(),
            patient.getFirstName(),
            patient.getLastName(),
            patient.getEmail(),
            patient.getPhoneNumber(),
            patient.isEmailVerified(),
            patient.isPhoneVerified(),
            patient.isActive()
        );
    }
} 