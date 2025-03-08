package com.example.chat_backend.controller.rest.impl;

import java.util.List;
import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat_backend.controller.rest.IChatMessageResource;
import com.example.chat_backend.service.ChatMessageService;
import com.example.chat_backend.service.dto.message.ChatMessageDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatMessageResource implements IChatMessageResource {
    private final ChatMessageService chatMessageService;

    @Override
    public ResponseEntity<List<ChatMessageDTO>> getChatMessages(UUID roomId,
                                                                @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(
                chatMessageService.query(roomId, pageable));
    }
}
