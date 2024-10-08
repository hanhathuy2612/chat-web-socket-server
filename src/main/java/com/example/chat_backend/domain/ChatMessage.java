package com.example.chat_backend.domain;

import com.example.chat_backend.domain.enumerate.MessageType;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Entity
@Table(name = "chat_message")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ChatMessage extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private AppUser sender;

    public ChatMessage(ChatMessageDTO dto) {
        this.id = dto.getId();
        this.type = dto.getType();
        this.content = dto.getContent();
        if (Objects.nonNull(dto.getSender())) {
            this.sender = new AppUser(dto.getSender());
        }
    }
}
