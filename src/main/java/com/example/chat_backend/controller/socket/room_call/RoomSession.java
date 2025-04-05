package com.example.chat_backend.controller.socket.room_call;

import lombok.Getter;
import org.kurento.client.MediaPipeline;
import org.kurento.client.WebRtcEndpoint;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class RoomSession {
    private final UUID roomId;
    private final MediaPipeline pipeline;
    private final Map<UUID, UserSession> participants = new ConcurrentHashMap<>();

    public RoomSession(UUID roomId, MediaPipeline pipeline) {
        this.roomId = roomId;
        this.pipeline = pipeline;
    }

    public void addParticipant(UUID userId) {
        WebRtcEndpoint webRtcEndpoint = new WebRtcEndpoint.Builder(pipeline).build();
        participants.put(userId, new UserSession(userId, webRtcEndpoint));
    }

    public void removeParticipant(UUID userId) {
        UserSession userSession = participants.remove(userId);
        if (userSession != null) {
            userSession.getWebRtcEndpoint().release();
        }
    }
}
