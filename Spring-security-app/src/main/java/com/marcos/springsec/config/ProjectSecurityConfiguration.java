package com.marcos.springsec.config;

import com.marcos.springsec.config.cors.CustomCorsConfigurationSource;
import com.marcos.springsec.exception.CustomAccessDeniedHandler;
import com.marcos.springsec.exception.CustomBasicEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import static com.marcos.springsec.constants.PathConstants.*;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class ProjectSecurityConfiguration {

    private final String[] AUTHENTICATED_PATHS = {ACCOUNT, BALANCE, CARDS, LOANS};
    private final String[] ALLOWED_PATHS = {CONTACT, NOTICES, ERROR, CUSTOMER, INVALID_SESSION, REGISTER, USER};
    private final String[] CSRF_IGNORE = {CONTACT, REGISTER};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, CustomBasicEntryPoint customBasicEntryPoint) throws Exception {

/*
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        http.securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .ignoringRequestMatchers(CSRF_IGNORE)
                ).addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);

        http.cors(cors -> cors.configurationSource(customConfigurationSource()));
*/

        // Comentar o código acima, e descomentar o abaixo para testes
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.invalidSessionUrl(INVALID_SESSION).maximumSessions(1).maxSessionsPreventsLogin(true));

        http.authorizeHttpRequests((request) -> request
                .requestMatchers(AUTHENTICATED_PATHS).authenticated()
                .requestMatchers(ALLOWED_PATHS).permitAll());

        http.formLogin(withDefaults());
        http.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(customBasicEntryPoint));
        http.exceptionHandling(exception -> exception.accessDeniedHandler(new CustomAccessDeniedHandler()));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource customConfigurationSource() {
        return new CustomCorsConfigurationSource();
    }
}


