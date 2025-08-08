package com.example.demo.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRegistrationResponse {
    private boolean success;
    private String message;
    private PatientData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientData {
        private UUID patientId;
        private String email;
        private String phoneNumber;
        private boolean emailVerified;
        private boolean phoneVerified;
    }
} 