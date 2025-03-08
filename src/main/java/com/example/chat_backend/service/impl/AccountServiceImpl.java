package com.example.chat_backend.service.impl;

import com.example.chat_backend.config.security.SecurityUtils;
import com.example.chat_backend.controller.rest.dto.request.GetContactRequest;
import com.example.chat_backend.domain.AppUser;
import com.example.chat_backend.domain.Authority;
import com.example.chat_backend.domain.UserContact;
import com.example.chat_backend.domain.enumerate.ContactStatus;
import com.example.chat_backend.domain.enumerate.OnlineStatus;
import com.example.chat_backend.mapper.AppUserMapper;
import com.example.chat_backend.repository.AppUserRepository;
import com.example.chat_backend.repository.UserContactRepository;
import com.example.chat_backend.service.AccountService;
import com.example.chat_backend.service.dto.user.AppUserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AppUserRepository appUserRepository;
    private final UserContactRepository userContactRepository;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUserDTO signup(AppUserDTO user) {
        if (existsByUsername(user.getLogin())) {
            throw new RuntimeException("Username is already in use with: " + user.getLogin());
        }
        AppUser appUser = new AppUser(user);
        appUser.setPassword(passwordEncoder.encode(user.getPassword()));
        appUser.setAuthorities(
            Set.of(
                new Authority().name("ROLE_USER")
            )
        );
        appUser.setActivated(true);
        appUser = appUserRepository.save(appUser);
        return new AppUserDTO(appUser);
    }

    @Override
    public void addContact(AppUserDTO contact, ContactStatus status) {
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
            () -> new RuntimeException("User not logged in")
        );

        if (Objects.equals(username, contact.getLogin())) {
            throw new RuntimeException("Username is already in use with: " + contact.getLogin());
        }

        AppUser appUser = this.appUserRepository.findOneWithAuthoritiesByLogin(username).orElseThrow(
            () -> new RuntimeException("User not found")
        );

        UserContact userContact = new UserContact();
        userContact.setUser(appUser);
        userContact.setContact(appUserMapper.toEntity(contact));
        userContact.setStatus(status);
        userContactRepository.save(userContact);
    }

    @Override
    public AppUserDTO getAccountInfo() {
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
            () -> new RuntimeException("User not logged in")
        );

        return this.appUserRepository.findOneWithAuthoritiesByLogin(username).map(AppUserDTO::new).orElseThrow(
            () -> new RuntimeException("User not found")
        );
    }

    @Override
    public List<AppUserDTO> getContacts(GetContactRequest queryParams, Pageable pageable) {
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
            () -> new RuntimeException("User not logged in")
        );
        Page<AppUserDTO> appUserDTOPage = appUserRepository
            .findAllByContactOf_Login(username, null, pageable)
            .map(AppUserDTO::new);
        return appUserDTOPage.getContent();
    }

    @Override
    public void updateOnlineStatus(String email, String sessionId, OnlineStatus onlineStatus) {
        AppUser appUser = appUserRepository.findOneWithAuthoritiesByLogin(email).orElseThrow(
            () -> new RuntimeException("User not found with email: " + email)
        );
        appUser.setOnline(onlineStatus == OnlineStatus.ONLINE);
        appUser.setSocketSessionId(sessionId);
        appUserRepository.save(appUser);
    }

    private boolean existsByUsername(String username) {
        return appUserRepository.existsByLogin(username);
    }
}
