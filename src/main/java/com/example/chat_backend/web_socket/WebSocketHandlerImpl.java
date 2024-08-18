package com.example.chat_backend.web_socket;

import com.example.chat_backend.service.ChatMessageService;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import com.example.chat_backend.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class WebSocketHandlerImpl extends TextWebSocketHandler {

    private final ChatMessageService chatMessageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connected: " + session.getId());
        // You can store the session or send an initial message if needed
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        System.out.println("Received: " + payload);
        ChatMessageDTO chatMessageDTO = chatMessageService.create(payload);
        session.sendMessage(new TextMessage(ConvertUtil.convertToString(chatMessageDTO)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Disconnected: " + session.getId());
    }
}
