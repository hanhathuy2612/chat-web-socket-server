package com.example.chat_backend.service.impl;

import com.example.chat_backend.config.security.SecurityUtils;
import com.example.chat_backend.domain.AppUser;
import com.example.chat_backend.domain.Authority;
import com.example.chat_backend.repository.AppUserRepository;
import com.example.chat_backend.rest.request.ContactQueryParams;
import com.example.chat_backend.service.AccountService;
import com.example.chat_backend.service.dto.AppUserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
        appUser = appUserRepository.save(appUser);
        return new AppUserDTO(appUser);
    }

    @Override
    public void addContact(AppUserDTO contact) {
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new RuntimeException("User not logged in")
        );

        if (Objects.equals(username, contact.getLogin())) {
            throw new RuntimeException("Username is already in use with: " + contact.getLogin());
        }

        AppUser appUser = this.appUserRepository.findOneWithAuthoritiesByLogin(username).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        appUser.addContact(new AppUser(contact));
        appUserRepository.save(appUser);
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
    public List<AppUserDTO> getContacts(ContactQueryParams queryParams, Pageable pageable) {
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new RuntimeException("User not logged in")
        );

        return appUserRepository
                .findAllByContactOf_Login(username, pageable)
                .map(AppUserDTO::new)
                .getContent();
    }

    private boolean existsByUsername(String username) {
        return appUserRepository.existsByLogin(username);
    }
}
