package com.example.demo.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientLoginRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
} 