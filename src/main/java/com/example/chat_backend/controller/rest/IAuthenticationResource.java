package com.example.chat_backend.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.chat_backend.controller.rest.dto.request.LoginRequest;
import com.example.chat_backend.controller.rest.dto.request.RefreshTokenRequest;
import com.example.chat_backend.controller.rest.dto.response.TokenResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/api/authenticate")
public interface IAuthenticationResource {
    /**
     * {@code POST /authenticate/login} : login.
     * 
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @PostMapping("/login")
    ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request);

    /**
     * {@code POST /authenticate/refresh} : refresh authentication token.
     * 
     * @param request the refresh token request.
     * @return the new JWT token.
     */
    @PostMapping("/refresh")
    ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request);

    /**
     * {@code GET /authenticate} : check if the user is authenticated, and return
     * its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping
    String isAuthenticated(HttpServletRequest request);
}
