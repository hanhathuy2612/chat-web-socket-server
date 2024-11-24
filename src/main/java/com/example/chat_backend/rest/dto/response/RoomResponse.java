package com.example.chat_backend.rest.dto.response;

import java.util.List;

import com.example.chat_backend.service.dto.AppUserDTO;
import com.example.chat_backend.service.dto.ChatMessageDTO;

public record RoomResponse(
        Long id,
        String name,
        String description,
        ChatMessageDTO lastMessage,
        List<AppUserDTO> appUsers) {
}
