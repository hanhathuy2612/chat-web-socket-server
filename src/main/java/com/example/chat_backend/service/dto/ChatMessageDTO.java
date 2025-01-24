package com.example.chat_backend.service.dto;

import com.example.chat_backend.domain.ChatMessage;
import com.example.chat_backend.domain.enumerate.MessageType;
import com.example.chat_backend.service.dto.room.RoomDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDTO extends AuditDTO {

    private UUID id;

    private MessageType type;

    private String content;

    @JsonIgnoreProperties(value = { "rooms" }, allowSetters = true)
    private AppUserDTO sender;
    
    @JsonIgnoreProperties(value = { "lastMessage", "appUsers" }, allowSetters = true)
    private RoomDTO room;

    public ChatMessageDTO(ChatMessage chatMessage) {
        this.id = chatMessage.getId();
        this.content = chatMessage.getContent();
        this.type = chatMessage.getType();
        
        if (Objects.nonNull(chatMessage.getSender())) {
            this.sender = new AppUserDTO(chatMessage.getSender());
        }
    }
}
