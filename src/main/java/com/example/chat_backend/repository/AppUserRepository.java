package com.example.chat_backend.repository;

import com.example.chat_backend.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    @EntityGraph(attributePaths = "authorities")
    Optional<AppUser> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<AppUser> findOneWithAuthoritiesByEmailIgnoreCase(String login);

    boolean existsByLogin(String username);

    @Query("""
        select uc.contact from AppUser u
            join u.contacts uc
        where (:keyword is null or uc.user.login like concat('%', :keyword, '%'))
            and (:keyword is null or uc.user.firstName like concat('%', :keyword, '%'))
            and (:keyword is null or uc.user.lastName like concat('%', :keyword, '%'))
            and :username = u.login
        """)
    Page<AppUser> findAllByContactOf_Login(
        @Param("username") String username,
        @Param("keyword") String keyword,
        Pageable pageable
    );

    @Query("""
        select u from AppUser u
        where (:keyword is null or u.login like concat('%', :keyword, '%'))
            and (:keyword is null or u.firstName like concat('%', :keyword, '%'))
            and (:keyword is null or u.lastName like concat('%', :keyword, '%'))
        """)
    Page<AppUser> findPublicUsers(String keyword, Pageable pageable);
}
