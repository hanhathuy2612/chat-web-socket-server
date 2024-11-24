package com.example.chat_backend.rest;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.chat_backend.rest.dto.request.GetContactRequest;
import com.example.chat_backend.service.dto.AppUserDTO;

import jakarta.validation.Valid;

@RequestMapping("/api/account")
public interface IAccountResource {
    /**
     * {@code GET /account} : get account info.
     * 
     * @return the account info.
     */
    @GetMapping
    ResponseEntity<AppUserDTO> getAccountInfo();

    /**
     * {@code POST /account} : signup user info.
     * 
     * @param request the user info.
     * @return the user info.
     */
    @PostMapping
    ResponseEntity<AppUserDTO> signupUser(@RequestBody @Valid AppUserDTO request);

    /**
     * {@code POST /contacts} : add contact to user.
     * 
     * @param contact the contact info.
     * @return no content.
     */
    @PostMapping("/contacts")
    ResponseEntity<Void> addContact(@RequestBody AppUserDTO contact);

    /**
     * {@code GET /contacts} : get contacts of user.
     * 
     * @param queryParams the query params.
     * @param pageable    the pageable.
     * @return the contacts.
     */
    @GetMapping("/contacts")
    ResponseEntity<List<AppUserDTO>> getContacts(
            @ParameterObject GetContactRequest queryParams,
            @ParameterObject Pageable pageable
    );
}