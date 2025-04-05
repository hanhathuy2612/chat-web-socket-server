package com.example.chat_backend.config.kurento;

import org.kurento.client.KurentoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KurentoConfiguration {
    @Value("${app.kurento.url}")
    private String kurentoUrl;

    @Bean
    public KurentoClient kurentoClient() {
        return KurentoClient.create(kurentoUrl);
    }
}
