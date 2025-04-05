package com.example.chat_backend.controller.socket.room_call;

import lombok.RequiredArgsConstructor;
import org.kurento.client.MediaPipeline;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class RoomCallController {
    private final RoomCallManager roomManager;
    private final Map<UUID, RoomSession> activeRooms = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/join")
    public void joinRoom(@Payload JoinRoomPayload payload) {
        MediaPipeline pipeline = roomManager.getRoom(payload.roomId());
        RoomSession roomSession = activeRooms.computeIfAbsent(payload.roomId(), id -> new RoomSession(payload.roomId(), pipeline));
        roomSession.addParticipant(payload.userId());
        roomSession.getParticipants().forEach((key, userSession) -> {
            if (!key.equals(payload.userId())) {
                sendCallMessage(key, payload);
            }
        });
    }

    @MessageMapping("/leave")
    @SendTo("/topic/leave")
    public void leaveRoom(@Payload LeaveRoomPayload payload) {
        RoomSession roomSession = activeRooms.get(payload.roomId());
        if (roomSession != null) {
            roomSession.removeParticipant(payload.userId());
            if (roomSession.getParticipants().isEmpty()) {
                roomManager.closeRoom(payload.roomId());
                activeRooms.remove(payload.roomId());
            }

            roomSession.getParticipants().forEach((key, userSession) -> {
                if (!key.equals(payload.userId())) {
                    sendCallMessage(key, payload);
                }
            });
        }
    }

    private void sendCallMessage(UUID userId, Object payload) {
        messagingTemplate.convertAndSend("/topic/call/" + userId, payload);
    }
}
