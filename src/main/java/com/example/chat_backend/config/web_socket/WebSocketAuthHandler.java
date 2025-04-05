package com.example.chat_backend.config.web_socket;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

        SecurityContextHolder.getContext().setAuthentication(auth);
        log.info("✅ WebSocket Connected: " + auth.getName());
    }
}
