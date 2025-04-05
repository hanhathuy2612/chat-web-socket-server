package com.example.chat_backend.controller.rest;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.chat_backend.controller.rest.dto.request.SearchContactRequest;
import com.example.chat_backend.controller.rest.dto.response.SearchContactResponse;

@RequestMapping("api/v1/user-contacts")
public interface IUserContactResource {
    @GetMapping("search")
    ResponseEntity<SearchContactResponse> search(
            @ParameterObject SearchContactRequest request,
            @ParameterObject Pageable pageable);
}
