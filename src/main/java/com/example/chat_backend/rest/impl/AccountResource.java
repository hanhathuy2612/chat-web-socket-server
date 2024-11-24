package com.example.chat_backend.rest.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat_backend.rest.IAccountResource;
import com.example.chat_backend.rest.dto.request.GetContactRequest;
import com.example.chat_backend.service.AccountService;
import com.example.chat_backend.service.dto.AppUserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountResource implements IAccountResource {

    private final AccountService accountService;

    @Override
    public ResponseEntity<AppUserDTO> getAccountInfo() {
        log.info("getAccountInfo");
        return ResponseEntity.ok(
                accountService.getAccountInfo());
    }

    @Override
    public ResponseEntity<AppUserDTO> signupUser(AppUserDTO request) {
        log.info("signupUser: {}", request);
        return ResponseEntity.ok(
                accountService.signup(request));
    }

    @Override
    public ResponseEntity<Void> addContact(AppUserDTO contact) {
        log.info("addContact: {}", contact);
        accountService.addContact(contact);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<AppUserDTO>> getContacts(GetContactRequest request, Pageable pageable) {
        log.info("getContacts: {}", request);
        return ResponseEntity.ok(
                accountService.getContacts(request, pageable)
        );
    }
}
