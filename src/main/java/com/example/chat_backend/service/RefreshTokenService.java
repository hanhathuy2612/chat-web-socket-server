package com.example.chat_backend.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.chat_backend.domain.RefreshToken;
import com.example.chat_backend.exception.AuthenticationException;
import com.example.chat_backend.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    
    @Value("${app.security.authentication.jwt.refresh-token-validity-in-seconds:86400}")
    private long refreshTokenValidityInSeconds;
    
    private final RefreshTokenRepository refreshTokenRepository;
    
    @Transactional
    public RefreshToken createRefreshToken(String username) {
        // Delete any existing refresh token for this user
        refreshTokenRepository.deleteByUsername(username);
        
        // Create new refresh token
        RefreshToken refreshToken = RefreshToken.builder()
                .username(username)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(refreshTokenValidityInSeconds))
                .build();
        
        return refreshTokenRepository.save(refreshToken);
    }
    
    @Transactional(readOnly = true)
    public RefreshToken verifyExpiration(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(refreshToken -> {
                    if (refreshToken.isExpired()) {
                        refreshTokenRepository.delete(refreshToken);
                        throw new AuthenticationException("Refresh token expired. Please login again");
                    }
                    return refreshToken;
                })
                .orElseThrow(() -> new AuthenticationException("Invalid refresh token"));
    }
    
    @Transactional
    public void deleteByUsername(String username) {
        refreshTokenRepository.deleteByUsername(username);
    }
} 