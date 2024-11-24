package com.example.chat_backend.rest.dto.request;

import java.util.List;

import com.example.chat_backend.service.dto.AppUserDTO;

public record CreateRoomRequest(
        List<AppUserDTO> appUsers) {
}
