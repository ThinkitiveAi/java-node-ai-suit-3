package com.example.demo.Service;

import com.example.demo.Entity.Provider;
import com.example.demo.Repository.ProviderRepository;
import com.example.demo.Request.ProviderLoginRequest;
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
public class AuthService {
    private final ProviderRepository providerRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret:secret-key}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 1 day default
    private long jwtExpirationMs;

    public String authenticateProvider(ProviderLoginRequest request) {
        Optional<Provider> providerOpt = providerRepository.findByEmail(request.getEmail());
        if (providerOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), providerOpt.get().getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        Provider provider = providerOpt.get();
        return generateToken(provider);
    }

    private String generateToken(Provider provider) {
        return Jwts.builder()
                .setSubject(provider.getId().toString())
                .claim("email", provider.getEmail())
                .claim("role", "PROVIDER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();
    }
} 