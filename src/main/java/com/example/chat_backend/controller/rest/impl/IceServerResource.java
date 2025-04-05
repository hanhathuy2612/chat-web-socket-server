package com.example.chat_backend.controller.rest.impl;

import com.example.chat_backend.config.kurento.TurnConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IceServerResource {
    private final TurnConfiguration turnConfig;

    @GetMapping("/iceservers")
    public List<Map<String, String>> getIceServers() {
        return List.of(
            Map.of("urls", turnConfig.getStunServer()),
            Map.of(
                "urls", turnConfig.getTurnServer(),
                "username", turnConfig.getTurnUsername(),
                "credential", turnConfig.getTurnPassword()
            )
        );
    }
}
