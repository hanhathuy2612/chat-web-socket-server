package com.example.chat_backend.repository;

import com.example.chat_backend.domain.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, UUID> {
}
