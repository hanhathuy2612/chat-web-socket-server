package com.example.chat_backend.service.dto.room;

import com.example.chat_backend.service.dto.user.AppUserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RoomMemberDTO {
    private UUID id;

    @JsonIgnoreProperties(value = {"rooms"}, allowSetters = true)
    private AppUserDTO member;

    @JsonIgnoreProperties(value = {"lastMessage", "members"}, allowSetters = true)
    private RoomDTO room;
}
