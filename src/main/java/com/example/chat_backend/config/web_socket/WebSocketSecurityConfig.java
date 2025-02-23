package com.example.chat_backend.config.web_socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@Configuration
@EnableWebSocketSecurity
public class WebSocketSecurityConfig {

    @Bean
    public MessageMatcherDelegatingAuthorizationManager messageAuthorizationManager() {
        return (MessageMatcherDelegatingAuthorizationManager) MessageMatcherDelegatingAuthorizationManager.builder()
            .simpDestMatchers("/app/**").authenticated()
            .anyMessage().authenticated()
            .build();
    }
}
