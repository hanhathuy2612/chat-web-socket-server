package com.example.chat_backend.service.impl;

import com.example.chat_backend.config.security.SecurityUtils;
import com.example.chat_backend.mapper.AppUserMapper;
import com.example.chat_backend.repository.AppUserRepository;
import com.example.chat_backend.service.UserContactService;
import com.example.chat_backend.service.dto.user.AppUserDTO;
import com.example.chat_backend.service.dto.user.ExistingContactQuery;
import com.example.chat_backend.service.dto.user.PublicContactQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserContactServiceImpl implements UserContactService {
    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    @Override
    public Page<AppUserDTO> searchPublicContact(PublicContactQuery query) {
        return appUserRepository.findPublicUsers(query.keyword(), query.pageable())
            .map(appUserMapper::toDto);
    }

    @Override
    public Page<AppUserDTO> searchExistingContact(ExistingContactQuery query) {
        String login = SecurityUtils.getCurrentUserLoginOrThrow();
        return appUserRepository.findAllByContactOf_Login(login, query.keyword(), query.pageable())
            .map(appUserMapper::toDto);
    }
}
