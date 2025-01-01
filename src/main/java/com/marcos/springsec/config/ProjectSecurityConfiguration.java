package com.marcos.springsec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
@Configuration
// Indica que a classe define uma ou mais configurações de beans que serão gerenciados pelo container do spring
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
        http.formLogin(withDefaults()); // http.formLogin(AbstractHttpConfigurer::disable); sempre que formos invocar o método devemos passar a configuração obrigatória por meio de lambdas ou Method References (.disable() está depreciado) http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable()). Sempre quando ativamos o formLogin a extração das credenciais da request será realizada pela classe UsernamePasswordAuthenticationFilter com o método Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response). Mas caso utilizarmos o http basic style of login, será o BasicAuthenticationFilter em doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        http.httpBasic(withDefaults()); //http.httpBasic(HttpBasicConfigurer::disable);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}12345").authorities("read").build();
        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$DTzG57.5qOiRdyQrtelWx.Vu5ySjiC8Nd6b2Hp.IzsMtNVasd6prW").authorities("admin").build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}