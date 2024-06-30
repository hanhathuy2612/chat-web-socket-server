package com.example.chat_backend.controller;

import com.example.chat_backend.service.ChatMessageService;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessageDTO send(@Payload ChatMessageDTO chatMessage) {
        chatMessage = chatMessageService.create(chatMessage);
        return chatMessage;
    }
}
