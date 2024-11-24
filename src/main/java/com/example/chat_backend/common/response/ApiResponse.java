package com.example.chat_backend.common.response;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public record ApiResponse<T>(
        String requestId,
        LocalDateTime timestamp,
        T data,
        List<String> errors) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                data,
                null);
    }

    public static <T> ApiResponse<T> error(String... errors) {
        return new ApiResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                null,
                Arrays.asList(errors));
    }
}
