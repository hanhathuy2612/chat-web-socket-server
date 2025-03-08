package com.example.chat_backend.service.impl;

import com.example.chat_backend.domain.Room;
import com.example.chat_backend.mapper.AppUserMapper;
import com.example.chat_backend.mapper.RoomMapper;
import com.example.chat_backend.repository.RoomRepository;
import com.example.chat_backend.service.RoomService;
import com.example.chat_backend.service.dto.room.CreateRoomCommand;
import com.example.chat_backend.service.dto.room.RoomDTO;
import com.example.chat_backend.service.dto.room.RoomMemberDTO;
import com.example.chat_backend.service.dto.user.AppUserDTO;
import com.example.chat_backend.util.CurrentAppUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final AppUserMapper appUserMapper;
    private final CurrentAppUserUtil currentAppUserUtil;
    private final RoomMapper roomMapper;

    @Override
    public Page<RoomDTO> getUserRooms(String username, Pageable pageable) {
        return roomRepository
            .findByRoomMembers_Member_login(username, pageable)
            .map(roomMapper::toDto)
            .map(roomDTO -> mapRoomName(roomDTO, username));
    }

    @Override
    public RoomDTO create(CreateRoomCommand command) {
        AppUserDTO currentAppUser = currentAppUserUtil.getCurrentAppUserDTO();
        command.members().add(currentAppUser);

        // Get emails from roomDTO once
        Set<UUID> appUserIds = command.members().stream()
            .map(AppUserDTO::getId)
            .collect(Collectors.toSet());

        // Query join fetch to avoid N+1
        Boolean roomExists = roomRepository.existsRoomWithExactUsers(appUserIds, appUserIds.size());
        if (Boolean.TRUE.equals(roomExists)) {
            throw new RuntimeException("Room already exists between these users");
        }

        Room room = roomMapper.toEntity(command);
        for (AppUserDTO appUserDTO : command.members()) {
            room.addMember(appUserMapper.toEntity(appUserDTO));
        }
        room = roomRepository.save(room);
        return roomMapper.toDto(room);
    }

    private RoomDTO mapRoomName(RoomDTO roomDTO, String username) {
        AppUserDTO contact = roomDTO.getRoomMembers()
            .stream()
            .filter(user -> !user.getMember().getEmail().equals(username))
            .findFirst()
            .map(RoomMemberDTO::getMember)
            .orElse(null);

        if (Objects.isNull(contact)) {
            return roomDTO;
        }

        roomDTO.setName(contact.getFirstName() + " " + contact.getLastName());
        return roomDTO;
    }

    @Override
    public RoomDTO getRoomWithExactUsers(List<String> emails) {
        Optional<Room> optionalRoom = roomRepository.findRoomWithExactUsers(new HashSet<>(emails), emails.size());
        return optionalRoom.map(roomMapper::toDto).orElse(null);
    }

    @Override
    public RoomDTO findById(UUID roomId) {
        return this.roomRepository.findById(roomId)
            .map(this.roomMapper::toDto)
            .orElse(null);
    }
}
