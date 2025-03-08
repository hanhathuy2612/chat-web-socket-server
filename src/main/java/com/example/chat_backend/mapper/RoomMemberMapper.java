package com.example.chat_backend.mapper;

import com.example.chat_backend.domain.RoomMember;
import com.example.chat_backend.service.dto.room.RoomMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoomMemberMapper {
    RoomMemberMapper INSTANCE = Mappers.getMapper(RoomMemberMapper.class);

    RoomMemberDTO toDTO(RoomMember roomMember);
}
