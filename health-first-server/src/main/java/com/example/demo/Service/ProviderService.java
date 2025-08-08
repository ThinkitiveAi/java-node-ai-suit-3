package com.example.demo.Service;

import com.example.demo.Entity.Provider;
import com.example.demo.Repository.ProviderRepository;
import com.example.demo.Request.ProviderRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderService {
    private final ProviderRepository providerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public Provider registerProvider(ProviderRegistrationRequest request) {
        if (providerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (providerRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number already in use");
        }
        if (providerRepository.findByLicenseNumber(request.getLicenseNumber()).isPresent()) {
            throw new IllegalArgumentException("License number already in use");
        }
        Provider provider = new Provider();
        provider.setFirstName(request.getFirstName());
        provider.setLastName(request.getLastName());
        provider.setEmail(request.getEmail());
        provider.setPhoneNumber(request.getPhoneNumber());
        provider.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        provider.setSpecialization(request.getSpecialization());
        provider.setLicenseNumber(request.getLicenseNumber());
        provider.setYearsOfExperience(request.getYearsOfExperience());
        provider.setClinicAddress(request.getClinicAddress());
        // verificationStatus, isActive, createdAt, updatedAt are set by default
        return providerRepository.save(provider);
    }
} 