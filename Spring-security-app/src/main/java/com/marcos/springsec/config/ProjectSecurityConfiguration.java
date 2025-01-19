package com.marcos.springsec.config;

import com.marcos.springsec.config.cors.CustomCorsConfigurationSource;
import com.marcos.springsec.exception.CustomAccessDeniedHandler;
import com.marcos.springsec.exception.CustomBasicEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static com.marcos.springsec.constants.PathConstants.*;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class ProjectSecurityConfiguration {

    private final String[] AUTHENTICATED_PATHS = {ACCOUNT, BALANCE, CARDS, LOANS};
    private final String[] ALLOWED_PATHS = {CONTACT, NOTICES, ERROR, CUSTOMER, INVALID_SESSION, REGISTER};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, CustomBasicEntryPoint customBasicEntryPoint) throws Exception {

        http.cors(cors ->
                cors.configurationSource(customConfigurationSource()));

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.invalidSessionUrl(INVALID_SESSION).maximumSessions(1).maxSessionsPreventsLogin(true));

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


    @Bean
    public CorsConfigurationSource customConfigurationSource(){
        return new CustomCorsConfigurationSource();
    }
}
