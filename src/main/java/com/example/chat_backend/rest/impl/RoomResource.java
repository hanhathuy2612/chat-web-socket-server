package com.example.chat_backend.rest.impl;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat_backend.common.response.ApiResponse;
import com.example.chat_backend.common.response.PageResponse;
import com.example.chat_backend.config.security.SecurityUtils;
import com.example.chat_backend.mapper.RoomMapper;
import com.example.chat_backend.rest.IRoomResource;
import com.example.chat_backend.rest.dto.request.CreateRoomRequest;
import com.example.chat_backend.rest.dto.response.RoomResponse;
import com.example.chat_backend.service.RoomService;
import com.example.chat_backend.service.dto.room.RoomDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoomResource implements IRoomResource {
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @Override
    public ApiResponse<PageResponse<RoomResponse>> getUserRooms(@ParameterObject Pageable pageable) {
        log.info("Getting user rooms");
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new RuntimeException("User not logged in"));

        Page<RoomDTO> rooms = roomService.getUserRooms(username, pageable);
        PageResponse<RoomResponse> pageResponse = PageResponse.of(rooms.map(roomMapper::toResponse));
        return ApiResponse.success(pageResponse);
    }

    @Override
    public ResponseEntity<RoomDTO> getRoomByUsers(List<String> emails) {
        log.info("Getting room with exact users: {}", emails);
        return ResponseEntity.ok(
                roomService.getRoomWithExactUsers(emails));
    }

    @Override
    public ResponseEntity<ApiResponse<RoomDTO>> create(@RequestBody CreateRoomRequest request) {
        log.info("Creating room: {}", request);
        var createRoomCommand = roomMapper.toCreateCommand(request);
        return ResponseEntity.ok(
                ApiResponse.success(roomService.create(createRoomCommand)));
    }
}
