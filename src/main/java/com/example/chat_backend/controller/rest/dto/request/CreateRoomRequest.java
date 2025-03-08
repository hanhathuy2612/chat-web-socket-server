package com.example.chat_backend.controller.rest.dto.request;

import java.util.List;

import com.example.chat_backend.service.dto.user.AppUserDTO;

public record CreateRoomRequest(
        List<AppUserDTO> members) {
}
