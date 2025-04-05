package com.example.chat_backend.controller.socket.room_call;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kurento.client.WebRtcEndpoint;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserSession {
    private UUID userId;
    private WebRtcEndpoint webRtcEndpoint;

    public UserSession(UUID userId, WebRtcEndpoint webRtcEndpoint) {
        this.userId = userId;
        this.webRtcEndpoint = webRtcEndpoint;
    }
}
