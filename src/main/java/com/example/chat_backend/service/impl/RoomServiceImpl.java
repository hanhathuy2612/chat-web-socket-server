package com.example.chat_backend.service.impl;

import com.example.chat_backend.domain.Room;
import com.example.chat_backend.repository.RoomRepository;
import com.example.chat_backend.service.RoomService;
import com.example.chat_backend.service.dto.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public List<RoomDTO> getUserRooms(String username, Pageable pageable) {
        return roomRepository
                .findByAppUsers_Login(username, pageable)
                .map(RoomDTO::new)
                .getContent();
    }

    @Override
    public RoomDTO create(RoomDTO roomDTO) {
        Room room = new Room(roomDTO);
        room = roomRepository.save(room);
        return new RoomDTO(room);
    }
}
