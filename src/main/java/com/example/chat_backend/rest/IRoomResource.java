package com.example.chat_backend.rest;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.chat_backend.common.response.ApiResponse;
import com.example.chat_backend.common.response.PageResponse;
import com.example.chat_backend.rest.dto.request.CreateRoomRequest;
import com.example.chat_backend.rest.dto.response.RoomResponse;
import com.example.chat_backend.service.dto.room.RoomDTO;

@RequestMapping("api/rooms")
public interface IRoomResource {
    /**
     * {@code GET /} : get user rooms.
     * 
     * @param pageable the pageable.
     * @return the rooms.
     */
    @GetMapping
    ApiResponse<PageResponse<RoomResponse>> getUserRooms(@ParameterObject Pageable pageable);

    /**
     * {@code POST /by-users} : get room with exact users.
     * 
     * @param emails the users email.
     * @return the room.
     */
    @PostMapping("/by-users")
    ResponseEntity<RoomDTO> getRoomByUsers(@RequestBody List<String> emails);

    /**
     * {@code POST /} : create a room.
     * 
     * @param request the room dto.
     * @return the room.
     */
    @PostMapping
    ResponseEntity<ApiResponse<RoomDTO>> create(@RequestBody CreateRoomRequest request);
}
