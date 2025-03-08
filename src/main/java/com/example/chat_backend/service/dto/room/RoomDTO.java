package com.example.chat_backend.service.dto.room;

import com.example.chat_backend.service.dto.AuditDTO;
import com.example.chat_backend.service.dto.message.ChatMessageDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class RoomDTO extends AuditDTO {
    private UUID id;

    private String name;

    private String description;

    @JsonIgnoreProperties(value = {"room"}, allowSetters = true)
    private ChatMessageDTO lastMessage;

    @Builder.Default
    @JsonIgnoreProperties(value = {"room"}, allowSetters = true)
    private List<RoomMemberDTO> roomMembers = new ArrayList<>();

    public RoomDTO(UUID roomId) {
        this.id = roomId;
    }
}
