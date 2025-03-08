package com.example.chat_backend.service.dto.user;


import org.springframework.data.domain.Pageable;

public record PublicContactQuery(
    String keyword,
    Pageable pageable
) {
}
