package com.example.chat_backend.domain;

import com.example.chat_backend.config.Constants;
import com.example.chat_backend.service.dto.user.AppUserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class AppUser extends AbstractAuditingEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @Builder.Default
    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Builder.Default
    @Column(nullable = false, name = "is_online")
    private boolean isOnline = false;

    @Column(name = "socket_session_id")
    private String socketSessionId;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Builder.Default
    @Column(name = "reset_date")
    private Instant resetDate = null;

    @Builder.Default
    @ManyToMany
    @JoinTable(
        name = "app_user_authority",
        joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {
            @JoinColumn(name = "authority_name", referencedColumnName = "name")}
    )
    private Set<Authority> authorities = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private Set<RoomMember> roomMembers = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private Set<UserContact> contacts = new HashSet<>();

    public AppUser(AppUserDTO dto) {
        this.id = dto.getId();
        this.login = dto.getLogin();
        this.email = dto.getEmail();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.activated = dto.isActivated();
    }
}
