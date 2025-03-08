package com.example.chat_backend.service;

import com.example.chat_backend.domain.enumerate.ContactStatus;
import com.example.chat_backend.domain.enumerate.OnlineStatus;
import com.example.chat_backend.controller.rest.dto.request.GetContactRequest;
import com.example.chat_backend.service.dto.user.AppUserDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    AppUserDTO signup(AppUserDTO user);

    void addContact(@Valid AppUserDTO contact, ContactStatus status);

    AppUserDTO getAccountInfo();

    List<AppUserDTO> getContacts(GetContactRequest queryParams, Pageable pageable);

    void updateOnlineStatus(String email, String sessionId, OnlineStatus onlineStatus);
}
