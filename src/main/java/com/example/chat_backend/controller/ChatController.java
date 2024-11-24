package com.example.chat_backend.controller;

import com.example.chat_backend.service.ChatMessageService;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo({"/topic/room/{roomId}", "/topic/rooms/updates"})
    public ChatMessageDTO sendMessage(@DestinationVariable Long roomId, ChatMessageDTO chatMessage) {
        log.info("Send message to room {}", roomId);
        chatMessage = chatMessageService.create(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.roomUpdates")
    @SendTo("/topic/rooms/updates")
    public List<ChatMessageDTO> getRoomUpdates() {
        log.info("Get room updates for homepage");
        return chatMessageService.query(null, Pageable.unpaged());
    }
}
