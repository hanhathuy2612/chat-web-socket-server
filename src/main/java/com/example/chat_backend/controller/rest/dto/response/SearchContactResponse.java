package com.example.chat_backend.controller.rest.dto.response;

import com.example.chat_backend.service.dto.user.AppUserDTO;

import java.util.List;

public record SearchContactResponse(
    List<AppUserDTO> contacts,
    List<AppUserDTO> existingContacts
) {
}
