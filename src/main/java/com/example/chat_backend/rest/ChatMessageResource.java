package com.example.chat_backend.rest;

import com.example.chat_backend.service.ChatMessageService;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/chat-messages")
@RequiredArgsConstructor
public class ChatMessageResource {
    private final ChatMessageService chatMessageService;

    @GetMapping("room/{roomId}")
    public ResponseEntity<List<ChatMessageDTO>> getChatMessages(@PathVariable("roomId") Long roomId, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(
                chatMessageService.query(roomId, pageable)
        );
    }
}
