package com.example.chat_backend.mapper;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.example.chat_backend.domain.AppUser;
import com.example.chat_backend.service.dto.AppUserDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.chat_backend.domain.ChatMessage;
import com.example.chat_backend.domain.Room;
import com.example.chat_backend.rest.dto.request.CreateRoomRequest;
import com.example.chat_backend.rest.dto.response.RoomResponse;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import com.example.chat_backend.service.dto.room.CreateRoomCommand;
import com.example.chat_backend.service.dto.room.RoomDTO;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "appUsers", source = "appUsers", qualifiedByName = "toAppUserDTOS")
    @Mapping(target = "lastMessage", source = "chatMessages", qualifiedByName = "toLastMessageDTO")
    RoomDTO toDto(Room room);

    Room toEntity(CreateRoomCommand command);

    CreateRoomCommand toCreateCommand(CreateRoomRequest request);

    RoomResponse toResponse(RoomDTO dto);

    @Named("toAppUserDTOS")
    @IterableMapping(qualifiedByName = "toAppUserDTO")
    List<AppUserDTO> toAppUserDTOS(Set<AppUser> appUsers);

    @Named("toAppUserDTO")
    @Mapping(target = "rooms", ignore = true)
    AppUserDTO toAppUserDTO(AppUser appUsers);

    @Named("toLastMessageDTO")
    default ChatMessageDTO toLastMessageDTO(Set<ChatMessage> chatMessages) {
        return chatMessages.stream()
                .max(Comparator.comparing(ChatMessage::getLastModifiedDate))
                .map(ChatMessageDTO::new)
                .orElse(null);
    }
}
