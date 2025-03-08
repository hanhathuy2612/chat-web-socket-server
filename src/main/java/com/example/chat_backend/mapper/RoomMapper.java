package com.example.chat_backend.mapper;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.example.chat_backend.domain.RoomMember;
import com.example.chat_backend.service.dto.room.RoomMemberDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.chat_backend.domain.ChatMessage;
import com.example.chat_backend.domain.Room;
import com.example.chat_backend.controller.rest.dto.request.CreateRoomRequest;
import com.example.chat_backend.controller.rest.dto.response.RoomResponse;
import com.example.chat_backend.service.dto.message.ChatMessageDTO;
import com.example.chat_backend.service.dto.room.CreateRoomCommand;
import com.example.chat_backend.service.dto.room.RoomDTO;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "roomMembers", source = "roomMembers", qualifiedByName = "toRoomMembersDTO")
    @Mapping(target = "lastMessage", source = "chatMessages", qualifiedByName = "toLastMessageDTO")
    RoomDTO toDto(Room room);

    Room toEntity(CreateRoomCommand command);

    CreateRoomCommand toCreateCommand(CreateRoomRequest request);

    RoomResponse toResponse(RoomDTO dto);

    @Named("toRoomMembersDTO")
    @IterableMapping(qualifiedByName = "toMemberDTO")
    List<RoomMemberDTO> toRoomMembersDTO(Set<RoomMember> appUsers);

    @Named("toMemberDTO")
    @Mapping(target = "room", ignore = true)
    RoomMemberDTO toMemberDTO(RoomMember roomMember);

    @Named("toLastMessageDTO")
    default ChatMessageDTO toLastMessageDTO(Set<ChatMessage> chatMessages) {
        return chatMessages.stream()
                .max(Comparator.comparing(ChatMessage::getLastModifiedDate))
                .map(ChatMessageDTO::new)
                .orElse(null);
    }
}
