package com.example.chat_backend.service.impl;

import com.example.chat_backend.domain.Room;
import com.example.chat_backend.mapper.RoomMapper;
import com.example.chat_backend.repository.RoomRepository;
import com.example.chat_backend.service.RoomService;
import com.example.chat_backend.service.dto.AppUserDTO;
import com.example.chat_backend.service.dto.room.CreateRoomCommand;
import com.example.chat_backend.service.dto.room.RoomDTO;
import com.example.chat_backend.util.CurrentAppUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final CurrentAppUserUtil currentAppUserUtil;
    private final RoomMapper roomMapper;

    @Override
    public Page<RoomDTO> getUserRooms(String username, Pageable pageable) {
        return roomRepository
                .findByAppUsers_Login(username, pageable)
                .map(roomMapper::toDto)
                .map(roomDTO -> mapRoomName(roomDTO, username));
    }

    @Override
    public RoomDTO create(CreateRoomCommand command) {
        AppUserDTO currentAppUser = currentAppUserUtil.getCurrentAppUserDTO();
        // Add current user to list emails
        command.appUsers().add(currentAppUser);

        // Get emails from roomDTO once
        Set<String> newRoomEmails = command.appUsers().stream()
                .map(AppUserDTO::getEmail)
                .collect(Collectors.toSet());

        // Query join fetch to avoid N+1
        boolean roomExists = roomRepository.existsRoomWithExactUsers(newRoomEmails, newRoomEmails.size());
        if (roomExists) {
            throw new RuntimeException("Room already exists between these users");
        }

        Room room = roomMapper.toEntity(command);
        room = roomRepository.save(room);
        return roomMapper.toDto(room);
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

    @Override
    public RoomDTO getRoomWithExactUsers(List<String> emails) {
        Room room = roomRepository.findRoomWithExactUsers(new HashSet<>(emails), emails.size());
        return new RoomDTO(room);
    }
}
