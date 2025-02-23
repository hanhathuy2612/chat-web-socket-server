package com.example.chat_backend.config.web_socket;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class WebSocketSecurityAspect {

    @Around("@annotation(websocketPreAuthorize)")
    public Object checkRole(ProceedingJoinPoint joinPoint, WebsocketPreAuthorize websocketPreAuthorize) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getAuthorities().stream()
            .noneMatch(auth -> auth.getAuthority().equals(websocketPreAuthorize.value()))) {
            throw new AccessDeniedException("You do not have permission to perform this action");
        }

        return joinPoint.proceed();
    }
}
