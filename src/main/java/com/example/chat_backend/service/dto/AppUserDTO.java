package com.example.chat_backend.service.dto;

import com.example.chat_backend.domain.AppUser;
import com.example.chat_backend.domain.Room;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserDTO extends AuditDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private boolean activated = false;

    private String activationKey;

    private String resetKey;

    private Instant resetDate = null;

    @Builder.Default
    private List<AuthorityDTO> authorities = new ArrayList<>();

    @Builder.Default
    private Set<RoomDTO> rooms = new HashSet<>();

    public AppUserDTO(AppUser sender) {
        this.id = sender.getId();
        this.login = sender.getLogin();
        this.email = sender.getEmail();
        this.firstName = sender.getFirstName();
        this.lastName = sender.getLastName();
    }
}
