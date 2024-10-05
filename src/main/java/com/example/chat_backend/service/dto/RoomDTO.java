package com.example.chat_backend.service.dto;

import com.example.chat_backend.domain.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class RoomDTO extends AuditDTO {
    private Long id;

    private String name;

    @Builder.Default
    private List<AppUserDTO> appUsers = new ArrayList<>();

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.appUsers = room.getAppUsers().stream().map(AppUserDTO::new).toList();
    }
}
