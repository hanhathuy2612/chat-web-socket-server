package com.example.chat_backend.controller.rest.impl;

import com.example.chat_backend.controller.rest.UserContactResource;
import com.example.chat_backend.controller.rest.dto.request.SearchContactRequest;
import com.example.chat_backend.controller.rest.dto.response.SearchContactResponse;
import com.example.chat_backend.service.UserContactService;
import com.example.chat_backend.service.dto.user.AppUserDTO;
import com.example.chat_backend.service.dto.user.ExistingContactQuery;
import com.example.chat_backend.service.dto.user.PublicContactQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserContactResourceImpl implements UserContactResource {
    private final UserContactService userContactService;

    @Override
    public ResponseEntity<SearchContactResponse> search(SearchContactRequest request, Pageable pageable) {
        log.info("Search contact request: {}", request);
        Page<AppUserDTO> publicContacts = userContactService.searchPublicContact(new PublicContactQuery(request.keyword(), pageable));
        Page<AppUserDTO> existingContacts = userContactService.searchExistingContact(new ExistingContactQuery(request.keyword(), pageable));
        return ResponseEntity.ok(
            new SearchContactResponse(
                publicContacts.getContent(),
                existingContacts.getContent()
            )
        );
    }
}
