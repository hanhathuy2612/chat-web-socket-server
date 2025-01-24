package com.example.chat_backend.repository;

import com.example.chat_backend.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Page<Room> findByAppUsers_Login(String login, Pageable pageable);

    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Room r
            JOIN r.appUsers u
            GROUP BY r
            HAVING COUNT(DISTINCT u.email) = :userCount
            AND NOT EXISTS (
                SELECT u2
                FROM r.appUsers u2
                WHERE u2.email NOT IN :emails
            )
            """)
    Boolean existsRoomWithExactUsers(@Param("emails") Set<String> emails, @Param("userCount") int userCount);

    @Query("""
            SELECT r
            FROM Room r
            JOIN r.appUsers u
            GROUP BY r
            HAVING COUNT(u) = :userCount
            AND COUNT(CASE WHEN u.email IN :emails THEN 1 END) = :userCount
            """)
    Optional<Room> findRoomWithExactUsers(@Param("emails") Set<String> emails, @Param("userCount") int userCount);
}
