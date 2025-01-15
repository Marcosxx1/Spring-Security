package com.marcos.springsec.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomBasicEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // devemos também setar o status, assim
        response.setHeader("project-error-reason", "Authentication failed");
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
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                authException.getMessage(),
                request.getRequestURI()
        );

        // Write JSON response
        response.getWriter().write(jsonResponse);
    }
}
