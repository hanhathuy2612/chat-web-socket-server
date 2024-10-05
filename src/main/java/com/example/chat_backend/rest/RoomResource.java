package com.example.chat_backend.rest;

import com.example.chat_backend.config.security.SecurityUtils;
import com.example.chat_backend.service.RoomService;
import com.example.chat_backend.service.dto.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rooms")
@RequiredArgsConstructor
public class RoomResource {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getUserRooms(@ParameterObject Pageable pageable) {
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new RuntimeException("User not logged in")
        );

        return ResponseEntity.ok(
                roomService.getUserRooms(username, pageable)
        );
    }

    @PostMapping
    public ResponseEntity<RoomDTO> create(@RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(
                roomService.create(roomDTO)
        );
    }
}
