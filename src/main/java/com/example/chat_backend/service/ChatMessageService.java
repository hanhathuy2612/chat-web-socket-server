package com.example.chat_backend.service;

import com.example.chat_backend.service.dto.ChatMessageDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatMessageService {
    ChatMessageDTO create(ChatMessageDTO chatMessage);

    ChatMessageDTO create(String payload);

    List<ChatMessageDTO> query(Long roomId, Pageable pageable);
}
