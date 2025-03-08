package com.example.chat_backend.controller.rest.dto.request;

import com.example.chat_backend.domain.enumerate.ContactStatus;
import com.example.chat_backend.service.dto.user.AppUserDTO;

public record AddContactRequest(
    AppUserDTO contact,
    ContactStatus status
) {
}
