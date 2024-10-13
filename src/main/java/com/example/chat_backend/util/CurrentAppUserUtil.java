package com.example.chat_backend.util;

import com.example.chat_backend.config.security.SecurityUtils;
import com.example.chat_backend.domain.AppUser;
import com.example.chat_backend.repository.AppUserRepository;
import com.example.chat_backend.service.dto.AppUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentAppUserUtil {

    private final AppUserRepository appUserRepository;

    public AppUser getCurrentAppUser() {
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new RuntimeException("User not logged in")
        );

        return appUserRepository.findOneWithAuthoritiesByLogin(username).orElseThrow(
                () -> new RuntimeException("User not logged in")
        );
    }

    public AppUserDTO getCurrentAppUserDTO() {
        return new AppUserDTO(getCurrentAppUser());
    }
}
