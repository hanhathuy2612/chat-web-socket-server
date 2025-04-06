package com.example.chat_backend.controller.rest.dto.response;

public record TokenResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn
) {
}