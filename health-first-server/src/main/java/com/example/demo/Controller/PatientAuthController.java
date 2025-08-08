package com.example.demo.Controller;

import com.example.demo.Request.PatientLoginRequest;
import com.example.demo.Response.PatientLoginResponse;
import com.example.demo.Service.PatientAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientAuthController {
    private final PatientAuthService patientAuthService;

    @PostMapping("/login")
    public ResponseEntity<PatientLoginResponse> login(@Valid @RequestBody PatientLoginRequest request) {
        PatientLoginResponse response = patientAuthService.authenticatePatient(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }
} 