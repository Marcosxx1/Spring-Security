package com.marcos.springsec.config;

import com.marcos.springsec.exception.CustomAccessDeniedHandler;
import com.marcos.springsec.exception.CustomBasicEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.marcos.springsec.constants.PathConstants.*;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class ProjectSecurityConfiguration {

    private final String[] AUTHENTICATED_PATHS = {ACCOUNT, BALANCE, CARDS, LOANS};
    private final String[] ALLOWED_PATHS = {CONTACT, NOTICES, ERROR, CUSTOMER, INVALID_SESSION};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, CustomBasicEntryPoint customBasicEntryPoint) throws Exception {

        http.sessionManagement(session -> session.invalidSessionUrl(INVALID_SESSION).maximumSessions(1).maxSessionsPreventsLogin(true)); //  Podemos definir a quantidade máxima de sessões concorrentes também. Isso vai invaldar a sessão do usuário caso ele logue por outro navegador/meio he user who authenticates is allowed access and an existing user's session is expired.
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((request) -> request
                .requestMatchers(AUTHENTICATED_PATHS).authenticated()
                .requestMatchers(ALLOWED_PATHS).permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(customBasicEntryPoint));
        http.exceptionHandling(exception -> exception.accessDeniedHandler(new CustomAccessDeniedHandler()) );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
