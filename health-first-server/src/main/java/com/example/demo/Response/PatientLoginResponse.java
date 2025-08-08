package com.example.demo.Response;

import com.example.demo.Entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientLoginResponse {
    private boolean success;
    private String message;
    private LoginData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginData {
        private String accessToken;
        private int expiresIn;
        private String tokenType;
        private PatientData patient;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientData {
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private boolean emailVerified;
        private boolean phoneVerified;
        private boolean isActive;
    }
} 