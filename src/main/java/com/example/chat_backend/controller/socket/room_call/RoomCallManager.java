package com.example.chat_backend.controller.socket.room_call;

import lombok.RequiredArgsConstructor;
import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RoomCallManager {
    private final Map<UUID, MediaPipeline> rooms = new ConcurrentHashMap<>();
    private final KurentoClient kurentoClient;

    public MediaPipeline getRoom(UUID roomId) {
        return rooms.computeIfAbsent(roomId, k -> kurentoClient.createMediaPipeline());
    }

    public void closeRoom(UUID roomId) {
        MediaPipeline pipeline = rooms.remove(roomId);
        if (pipeline != null) {
            pipeline.release();
        }
    }
}
