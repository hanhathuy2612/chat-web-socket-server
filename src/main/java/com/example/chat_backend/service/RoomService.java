package com.example.chat_backend.service;

import com.example.chat_backend.service.dto.RoomDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {
    List<RoomDTO> getUserRooms(String username, Pageable pageable);

    RoomDTO create(RoomDTO roomDTO);
}
