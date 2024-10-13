package com.example.chat_backend.repository;

import com.example.chat_backend.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findByAppUsers_Login(String login, Pageable pageable);
}
