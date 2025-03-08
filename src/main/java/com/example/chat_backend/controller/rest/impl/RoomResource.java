package com.example.chat_backend.controller.rest.impl;

import com.example.chat_backend.common.response.ApiResponse;
import com.example.chat_backend.common.response.PageResponse;
import com.example.chat_backend.config.security.SecurityUtils;
import com.example.chat_backend.mapper.RoomMapper;
import com.example.chat_backend.controller.rest.IRoomResource;
import com.example.chat_backend.controller.rest.dto.request.CreateRoomRequest;
import com.example.chat_backend.controller.rest.dto.response.RoomResponse;
import com.example.chat_backend.service.RoomService;
import com.example.chat_backend.service.dto.room.RoomDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        RoomDTO roomDTO = roomService.getRoomWithExactUsers(emails);
        if (roomDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomDTO);
    }

    @Override
    public ResponseEntity<ApiResponse<RoomDTO>> create(@RequestBody CreateRoomRequest request) {
        log.info("Creating room: {}", request);
        var createRoomCommand = roomMapper.toCreateCommand(request);
        RoomDTO roomCreated = roomService.create(createRoomCommand);
        return ResponseEntity.ok(
                ApiResponse.success(roomCreated)
        );
    }
}
