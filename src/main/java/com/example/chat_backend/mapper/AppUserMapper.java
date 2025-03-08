package com.example.chat_backend.mapper;

import com.example.chat_backend.domain.AppUser;
import com.example.chat_backend.service.dto.user.AppUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "resetDate", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "resetKey", ignore = true)
    @Mapping(target = "activationKey", ignore = true)
    AppUserDTO toDto(AppUser appUser);

    AppUser toEntity(AppUserDTO contact);
}
