package com.example.chat_backend.config.kurento;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TurnConfiguration {
    @Value("${app.kurento.webrtc.stun}")
    private String stunServer;

    @Value("${app.kurento.webrtc.turn.url}")
    private String turnServer;

    @Value("${app.kurento.webrtc.turn.username}")
    private String turnUsername;

    @Value("${app.kurento.webrtc.turn.password}")
    private String turnPassword;
}
