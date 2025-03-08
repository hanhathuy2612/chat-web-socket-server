package com.example.chat_backend.service.dto.message;

import java.util.Objects;
import java.util.UUID;

import com.example.chat_backend.domain.ChatMessage;
import com.example.chat_backend.domain.enumerate.MessageType;
import com.example.chat_backend.service.dto.AuditDTO;
import com.example.chat_backend.service.dto.room.RoomDTO;
import com.example.chat_backend.service.dto.user.AppUserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDTO extends AuditDTO {

    private UUID id;

    private MessageType type;

    private String content;

    @JsonIgnoreProperties(value = { "rooms" }, allowSetters = true)
    private AppUserDTO sender;
    
    @JsonIgnoreProperties(value = { "lastMessage", "members" }, allowSetters = true)
    private RoomDTO room;

    public ChatMessageDTO(ChatMessage chatMessage) {
        this.id = chatMessage.getId();
        this.content = chatMessage.getContent();
        this.type = chatMessage.getType();
        this.room = new RoomDTO(chatMessage.getRoom().getId());
        
        if (Objects.nonNull(chatMessage.getSender())) {
            this.sender = new AppUserDTO(chatMessage.getSender());
        }
    }
}
