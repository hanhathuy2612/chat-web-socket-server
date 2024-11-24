package com.example.chat_backend.rest.dto.response;

import java.util.List;

public record RoomListResponse(List<RoomResponse> rooms) {
}
