package com.example.chat_backend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chat_backend.domain.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    @EntityGraph(attributePaths = "authorities")
    Optional<AppUser> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<AppUser> findOneWithAuthoritiesByEmailIgnoreCase(String login);

    boolean existsByLogin(String username);

    Page<AppUser> findAllByContactOf_Login(String username, Pageable pageable);
}
