package com.example.chat_backend.service;

import com.example.chat_backend.service.dto.ChatMessageDTO;

public interface ChatMessageService {
    ChatMessageDTO create(ChatMessageDTO chatMessage);

    ChatMessageDTO create(String payload);
}
