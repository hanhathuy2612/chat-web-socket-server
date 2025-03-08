package com.example.chat_backend.controller.rest.dto.response;

import java.util.List;
import java.util.UUID;

import com.example.chat_backend.service.dto.user.AppUserDTO;
import com.example.chat_backend.service.dto.message.ChatMessageDTO;

public record RoomResponse(
        UUID id,
        String name,
        String description,
        ChatMessageDTO lastMessage,
        List<AppUserDTO> appUsers) {
}
