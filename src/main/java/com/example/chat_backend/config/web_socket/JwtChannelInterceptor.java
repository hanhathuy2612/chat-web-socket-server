package com.example.chat_backend.config.web_socket;

import com.example.chat_backend.util.WebsocketUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {
    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String token = WebsocketUtil.extractTokenFromStompHeader(accessor);

        if (token != null) {
            try {
                Jwt jwt = jwtDecoder.decode(token);
                Authentication authenticationToken = this.jwtAuthenticationConverter.convert(jwt);
                SecurityContext context = new SecurityContextImpl();
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
                accessor.setUser(authenticationToken);
            } catch (Exception e) {
                System.out.println("Invalid Token: " + e.getMessage());
                return null;
            }
        }
        return message;
    }

}
