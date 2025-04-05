package com.example.chat_backend.controller.rest.impl;

import static com.example.chat_backend.config.security.SecurityUtils.AUTHORITIES_KEY;
import static com.example.chat_backend.config.security.SecurityUtils.JWT_ALGORITHM;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat_backend.controller.rest.IAuthenticationResource;
import com.example.chat_backend.controller.rest.dto.request.LoginRequest;
import com.example.chat_backend.controller.rest.dto.request.RefreshTokenRequest;
import com.example.chat_backend.controller.rest.dto.response.TokenResponse;
import com.example.chat_backend.domain.RefreshToken;
import com.example.chat_backend.service.RefreshTokenService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationResource implements IAuthenticationResource {
    @Value("${app.security.authentication.jwt.token-validity-in-seconds:0}")
    private long tokenValidityInSeconds;
    @Value("${app.security.authentication.jwt.token-validity-in-seconds-for-remember-me:0}")
    private long tokenValidityInSecondsForRememberMe;

    private final JwtEncoder jwtEncoder;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public ResponseEntity<TokenResponse> login(LoginRequest request) {
        log.debug("REST request to login with username: {}", request.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create access token
        long expiresIn = request.isRememberMe() ? tokenValidityInSecondsForRememberMe : tokenValidityInSeconds;
        String accessToken = createToken(authentication, request.isRememberMe());

        // Create refresh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authentication.getName());

        // Build response
        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .build();

        // Set bearer token in header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        return new ResponseEntity<>(tokenResponse, httpHeaders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenRequest request) {
        log.debug("REST request to refresh token");

        // Verify and get refresh token
        RefreshToken refreshToken = refreshTokenService.verifyExpiration(request.getRefreshToken());
        String username = refreshToken.getUsername();

        // Create new access token
        String accessToken = createTokenFromUsername(username, false);
        long expiresIn = tokenValidityInSeconds;

        // Build response
        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken()) // Reuse the same refresh token
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .build();

        // Set bearer token in header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        return new ResponseEntity<>(tokenResponse, httpHeaders, HttpStatus.OK);
    }

    @Override
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    private String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        return createTokenFromClaims(authentication.getName(), authorities, rememberMe);
    }

    private String createTokenFromUsername(String username, boolean rememberMe) {
        // In a real application, you would need to load the user's authorities from
        // database
        // This is simplified for this example
        return createTokenFromClaims(username, "ROLE_USER", rememberMe);
    }

    private String createTokenFromClaims(String subject, String authorities, boolean rememberMe) {
        Instant now = Instant.now();
        Instant validity;
        if (rememberMe) {
            validity = now.plus(this.tokenValidityInSecondsForRememberMe, ChronoUnit.SECONDS);
        } else {
            validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);
        }

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(subject)
                .claim(AUTHORITIES_KEY, authorities)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}
