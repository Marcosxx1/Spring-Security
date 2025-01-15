package com.marcos.springsec.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value()); // devemos também setar o status, assim
        response.setHeader("project-forbidden-reason", "Authorization failed");
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse = String.format(
                "{" +
                        "\"timestamp\": \"%s\"," +
                        "\"status\": %d," +
                        "\"error\": \"%s\"," +
                        "\"message\": \"%s\"," +
                        "\"path\": \"%s\"" +
                        "}",
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                accessDeniedException.getMessage(),
                request.getRequestURI()
        );

        // Write JSON response
        response.getWriter().write(jsonResponse);
    }
}
