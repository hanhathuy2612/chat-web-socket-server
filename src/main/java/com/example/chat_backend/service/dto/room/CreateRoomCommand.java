package com.example.chat_backend.service.dto.room;

import java.util.List;

import com.example.chat_backend.service.dto.AppUserDTO;

public record CreateRoomCommand(
        String name,
        List<AppUserDTO> appUsers) {
}
