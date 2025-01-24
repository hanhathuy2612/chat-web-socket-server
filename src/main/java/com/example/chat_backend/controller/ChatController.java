package com.example.chat_backend.controller;

import com.example.chat_backend.service.ChatMessageService;
import com.example.chat_backend.service.RoomService;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import com.example.chat_backend.service.dto.room.RoomDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final RoomService roomService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/room/{roomId}/messages")
    public void sendMessage(@DestinationVariable UUID roomId, @Payload ChatMessageDTO chatMessage) {
        log.info("Send message to room {}", roomId);
        chatMessage = chatMessageService.create(chatMessage);

        RoomDTO room = this.roomService.findById(roomId);
        if (room == null) {
            return;
        }
        this.sendToUsersInRoom(chatMessage, room);
    }

    @MessageMapping("/chat.roomUpdates")
    @SendTo("/topic/rooms/updates")
    public List<ChatMessageDTO> getRoomUpdates() {
        log.info("Get room updates for homepage");
        return chatMessageService.query(null, Pageable.unpaged());
    }

    private void sendToUsersInRoom(ChatMessageDTO chatMessage, RoomDTO room) {
        room.getAppUsers()
                .forEach(user -> this.sendMessageToUser(user.getId(), chatMessage));
    }

    private void sendMessageToUser(UUID userId, ChatMessageDTO chatMessage) {
        simpMessagingTemplate.convertAndSend("/chat/user/" + userId, chatMessage);
    }
}
