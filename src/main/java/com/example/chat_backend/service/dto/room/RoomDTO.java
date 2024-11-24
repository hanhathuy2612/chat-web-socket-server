package com.example.chat_backend.service.dto.room;

import com.example.chat_backend.domain.ChatMessage;
import com.example.chat_backend.domain.Room;
import com.example.chat_backend.service.dto.AppUserDTO;
import com.example.chat_backend.service.dto.AuditDTO;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class RoomDTO extends AuditDTO {
    private Long id;

    private String name;

    private String description;

    @JsonIgnoreProperties(value = { "room" }, allowSetters = true)
    private ChatMessageDTO lastMessage;

    @Builder.Default
    @JsonIgnoreProperties(value = { "rooms" }, allowSetters = true)
    private List<AppUserDTO> appUsers = new ArrayList<>();

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.description = room.getDescription();
        this.appUsers = room.getAppUsers().stream().map(AppUserDTO::new).toList();
        this.lastMessage = room.getChatMessages().stream()
                .max(Comparator.comparing(ChatMessage::getLastModifiedDate))
                .map(ChatMessageDTO::new)
                .orElse(null);
    }
}
