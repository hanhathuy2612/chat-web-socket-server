package com.example.chat_backend.controller.socket.room_call;

import java.util.UUID;

public record LeaveRoomPayload(
    UUID userId,
    UUID roomId
) {
}
