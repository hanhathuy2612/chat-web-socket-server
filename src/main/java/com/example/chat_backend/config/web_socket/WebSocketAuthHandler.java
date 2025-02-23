package com.example.chat_backend.config.web_socket;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Component
public class WebSocketAuthHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        Authentication auth = (Authentication) attributes.get("auth");

        if (auth == null) {
            session.close();
            return;
        }

        // Lưu authentication vào SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println("✅ WebSocket Connected: " + auth.getName());
    }
}
