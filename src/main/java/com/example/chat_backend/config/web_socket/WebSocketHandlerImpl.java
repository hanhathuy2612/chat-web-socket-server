package com.example.chat_backend.config.web_socket;

import com.example.chat_backend.domain.enumerate.OnlineStatus;
import com.example.chat_backend.service.AccountService;
import com.example.chat_backend.service.ChatMessageService;
import com.example.chat_backend.service.dto.ChatMessageDTO;
import com.example.chat_backend.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandlerImpl extends TextWebSocketHandler {

    private final ChatMessageService chatMessageService;
    private final AccountService accountService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("Connected: {}", session.getId());
        // You can store the session or send an initial message if needed
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        try {
            String payload = message.getPayload();
            log.debug("Received: {}", payload);

            ChatMessageDTO chatMessageDTO = ConvertUtil.convertToObject(payload, ChatMessageDTO.class);

            handleOnlineStatus(chatMessageDTO, session);

            chatMessageDTO = chatMessageService.create(chatMessageDTO);

            session.sendMessage(new TextMessage(ConvertUtil.convertToString(chatMessageDTO)));
        } catch (Exception e) {
            log.error("handleTextMessage error: {}", e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.debug("Disconnected: {}", session.getId());
    }

    private void handleOnlineStatus(ChatMessageDTO chatMessageDTO, WebSocketSession session) {
        switch (chatMessageDTO.getType()) {
            case CONNECTED:
                accountService.updateOnlineStatus(chatMessageDTO.getSender().getEmail(), session.getId(), OnlineStatus.ONLINE);
                break;
            case DISCONNECTED:
                accountService.updateOnlineStatus(chatMessageDTO.getSender().getEmail(), session.getId(), OnlineStatus.OFFLINE);
        }
    }
}
