package com.example.chat_backend.service;

import com.example.chat_backend.rest.request.ContactQueryParams;
import com.example.chat_backend.service.dto.AppUserDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    AppUserDTO signup(AppUserDTO user);

    void addContact(@Valid AppUserDTO contact);

    AppUserDTO getAccountInfo();

    List<AppUserDTO> getContacts(ContactQueryParams queryParams, Pageable pageable);
}
