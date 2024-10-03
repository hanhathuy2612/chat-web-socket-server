package com.example.chat_backend.repository;

import com.example.chat_backend.domain.ChatMessage;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findAllByRoomId(Long roomId, Pageable pageable);
}
