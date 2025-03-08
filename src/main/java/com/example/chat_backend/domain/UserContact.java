package com.example.chat_backend.domain;

import com.example.chat_backend.domain.enumerate.ContactStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "user_contacts")
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class UserContact extends AbstractAuditingEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private AppUser contact;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactStatus status;
}
