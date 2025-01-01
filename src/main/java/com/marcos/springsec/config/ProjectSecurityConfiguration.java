package com.marcos.springsec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.marcos.springsec.constants.PathConstants.*;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Classe de configuração de segurança para o projeto.
 *
 * <p>
 * O Spring Security, por padrão, protege todas as rotas dentro da aplicação web. Esse comportamento
 * é definido pelo método `defaultSecurityFilterChain(HttpSecurity http)` da classe
 * `SpringBootWebSecurityConfiguration`. Podemos modificar esse comportamento para permitir ou
 * restringir o acesso a diferentes endpoints, como mostrado no código abaixo.
 * </p>
 *
 * <p>
 * Nesta configuração, definimos que as rotas em {@code AUTHENTICATED_PATHS} exigem autenticação,
 * enquanto as rotas em {@code ALLOWED_PATHS} são acessíveis a todos os usuários sem autenticação.
 * Além disso, o suporte para login baseado em formulário e autenticação HTTP básica é ativado.
 * </p>
 */
@Configuration // Indica que a classe define uma ou mais configurações de beans que serão gerenciados pelo container do spring
public class ProjectSecurityConfiguration {

    private final String[] AUTHENTICATED_PATHS = {ACCOUNT, BALANCE, CARDS, LOANS};

    private final String[] ALLOWED_PATHS = {CONTACT, NOTICES, "/error"};

    /**
     * Configura a cadeia de filtros de segurança do Spring Security.
     *
     * @param http objeto HttpSecurity usado para configurar as regras de segurança.
     * @return um SecurityFilterChain configurado.
     * @throws Exception caso ocorra algum erro na configuração.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((request) -> request
                .requestMatchers(AUTHENTICATED_PATHS).authenticated()
                .requestMatchers(ALLOWED_PATHS).permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }
}
