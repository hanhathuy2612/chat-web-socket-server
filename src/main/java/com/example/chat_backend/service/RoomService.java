package com.example.chat_backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.chat_backend.service.dto.room.CreateRoomCommand;
import com.example.chat_backend.service.dto.room.RoomDTO;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    /**
     * Get user rooms.
     * 
     * @param username the username.
     * @param pageable the pageable.
     * @return the rooms.
     */
    Page<RoomDTO> getUserRooms(String username, Pageable pageable);

    /**
     * Create a room.
     * 
     * @param command the create room command.
     * @return the room.
     */
    RoomDTO create(CreateRoomCommand command);

    /**
     * Get room with exact users.
     * 
     * @param emails the users email.
     * @return the room.
     */
    RoomDTO getRoomWithExactUsers(List<String> emails);

    /**
     * Find room by id
     * @param roomId room id
     * @return the room
     */
    RoomDTO findById(UUID roomId);
}
