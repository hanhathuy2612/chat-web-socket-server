package com.example.chat_backend.domain;

import com.example.chat_backend.service.dto.RoomDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Room extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "room_app_user",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "app_user_id")
    )
    private Set<AppUser> appUsers = new HashSet<>();


    @OneToMany(mappedBy = "room")
    private Set<ChatMessage> chatMessages = new HashSet<>();

    public Room(RoomDTO roomDTO) {
        this.id = roomDTO.getId();
        this.name = roomDTO.getName();
        this.appUsers = roomDTO.getAppUsers().stream().map(AppUser::new).collect(Collectors.toSet());
    }
}
