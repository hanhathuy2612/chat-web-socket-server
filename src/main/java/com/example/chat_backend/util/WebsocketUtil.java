package com.example.chat_backend.util;

import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class WebsocketUtil {
    WebsocketUtil() {
    }

    public static String extractTokenFromStompHeader(StompHeaderAccessor accessor) {
        String authHeader = accessor.getFirstNativeHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        String nativeUrl = accessor.getFirstNativeHeader("nativeUrl");

        if (nativeUrl != null && nativeUrl.contains("?")) {
            String query = nativeUrl.substring(nativeUrl.indexOf("?") + 1);

            MultiValueMap<String, String> queryParams = UriComponentsBuilder.newInstance()
                .query(query)
                .build()
                .getQueryParams();

            return queryParams.getFirst("token");
        }

        return null;
    }

    public static String extractTokenFromUrl(ServletServerHttpRequest request) {
        Map<String, String[]> params = request.getServletRequest().getParameterMap();
        if (params.containsKey("token")) {
            return params.get("token")[0];
        }
        return null;
    }
}
