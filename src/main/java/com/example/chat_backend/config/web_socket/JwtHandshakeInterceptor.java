package com.example.chat_backend.config.web_socket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            String token = extractTokenFromUrl(servletRequest);
            if (token == null || token.isEmpty()) {
                return false;
            }

            try {
                Jwt jwt = jwtDecoder.decode(token);
                if (Objects.nonNull(jwt)) {
                    Authentication authenticationToken = this.jwtAuthenticationConverter.convert(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    attributes.put("username", authenticationToken.getName());
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    private String extractTokenFromUrl(ServletServerHttpRequest request) {
        Map<String, String[]> params = request.getServletRequest().getParameterMap();
        if (params.containsKey("token")) {
            return params.get("token")[0];
        }
        return null;
    }
}
