package com.example.chat_backend.controller;

import com.example.chat_backend.service.ChatMessageService;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send")
    public ChatMessageDTO send(@Payload ChatMessageDTO chatMessage) {
        chatMessage = chatMessageService.create(chatMessage);

        // Assuming `chatMessage` contains a `roomId` field to identify the room
        String destination = "/topic/messages/" + chatMessage.getRoom().getId();
        messagingTemplate.convertAndSend(destination, chatMessage);

        return chatMessage;
    }
}
