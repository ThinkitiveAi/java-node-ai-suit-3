package com.example.demo.Controller;

import com.example.demo.Request.PatientRegistrationRequest;
import com.example.demo.Response.PatientRegistrationResponse;
import com.example.demo.Response.ValidationErrorResponse;
import com.example.demo.Service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody PatientRegistrationRequest request) {
        PatientRegistrationResponse response = patientService.registerPatient(request);
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String[]> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            
            if (errors.containsKey(fieldName)) {
                String[] existingErrors = errors.get(fieldName);
                String[] newErrors = new String[existingErrors.length + 1];
                System.arraycopy(existingErrors, 0, newErrors, 0, existingErrors.length);
                newErrors[existingErrors.length] = errorMessage;
                errors.put(fieldName, newErrors);
            } else {
                errors.put(fieldName, new String[]{errorMessage});
            }
        });

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
            false,
            "Validation failed",
            errors
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }
} 