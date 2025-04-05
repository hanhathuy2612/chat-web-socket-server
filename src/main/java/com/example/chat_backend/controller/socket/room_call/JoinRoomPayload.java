package com.example.chat_backend.controller.socket.room_call;

import java.util.UUID;

public record JoinRoomPayload(
    UUID roomId,
    UUID userId) {
}
