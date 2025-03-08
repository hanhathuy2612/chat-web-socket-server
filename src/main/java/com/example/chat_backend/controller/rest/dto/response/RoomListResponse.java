package com.example.chat_backend.controller.rest.dto.response;

import java.util.List;

public record RoomListResponse(List<RoomResponse> rooms) {
}
