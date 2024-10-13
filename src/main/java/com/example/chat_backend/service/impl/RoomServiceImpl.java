package com.example.chat_backend.service.impl;

import com.example.chat_backend.domain.Room;
import com.example.chat_backend.repository.RoomRepository;
import com.example.chat_backend.service.RoomService;
import com.example.chat_backend.service.dto.AppUserDTO;
import com.example.chat_backend.service.dto.RoomDTO;
import com.example.chat_backend.util.CurrentAppUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final CurrentAppUserUtil currentAppUserUtil;

    @Override
    public List<RoomDTO> getUserRooms(String username, Pageable pageable) {
        return roomRepository
                .findByAppUsers_Login(username, pageable)
                .map(RoomDTO::new)
                .map(roomDTO -> mapRoomName(roomDTO, username))
                .getContent();
    }

    @Override
    public RoomDTO create(RoomDTO roomDTO) {
        AppUserDTO currentAppUser = currentAppUserUtil.getCurrentAppUserDTO();
        roomDTO.getAppUsers().add(currentAppUser);

        Room room = new Room(roomDTO);
        room = roomRepository.save(room);
        return new RoomDTO(room);
    }

    private RoomDTO mapRoomName(RoomDTO roomDTO, String username) {
        AppUserDTO contact = roomDTO.getAppUsers()
                .stream()
                .filter(user -> !user.getEmail().equals(username))
                .findFirst()
                .orElse(null);

        if (Objects.isNull(contact)) {
            return roomDTO;
        }

        roomDTO.setName(contact.getFirstName() + " " + contact.getLastName());
        return roomDTO;
    }
}
