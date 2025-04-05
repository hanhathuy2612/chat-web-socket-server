package com.example.chat_backend.domain;

import com.example.chat_backend.service.dto.room.RoomDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Room extends AbstractAuditingEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    private String description;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomMember> roomMembers = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "room")
    private Set<ChatMessage> chatMessages = new HashSet<>();

    public Room(RoomDTO roomDTO) {
        this.id = roomDTO.getId();
        this.name = roomDTO.getName();
    }

    public void addMember(AppUser appUser) {
        RoomMember member = new RoomMember();
        member.setRoom(this);
        member.setMember(appUser);
        roomMembers.add(member);
    }
}
