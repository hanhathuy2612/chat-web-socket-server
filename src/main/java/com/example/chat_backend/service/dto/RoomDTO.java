package com.example.chat_backend.service.dto;

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
}
