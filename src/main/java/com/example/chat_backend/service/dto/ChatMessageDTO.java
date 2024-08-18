package com.example.chat_backend.service.dto;

import com.example.chat_backend.domain.ChatMessage;
import com.example.chat_backend.domain.enumerate.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDTO extends AuditDTO {

    private Long id;

    private MessageType type;

    private String content;

    private AppUserDTO sender;
    
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
