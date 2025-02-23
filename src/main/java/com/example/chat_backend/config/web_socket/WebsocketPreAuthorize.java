package com.example.chat_backend.config.web_socket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebsocketPreAuthorize {
    String value(); // example ROLE_ADMIN
}
