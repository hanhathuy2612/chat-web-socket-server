package com.example.chat_backend.controller.rest;

import java.util.List;
import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.chat_backend.service.dto.message.ChatMessageDTO;

@RequestMapping("/api/chat-messages")
public interface IChatMessageResource {
    /**
     * {@code GET /room/{roomId}} : get chat messages of room.
     * 
     * @param roomId the room id.
     * @param pageable the pageable.
     * @return the chat messages.
     */
    @GetMapping("/room/{roomId}")
    ResponseEntity<List<ChatMessageDTO>> getChatMessages(@PathVariable("roomId") UUID roomId,
            @ParameterObject Pageable pageable);
}
