package com.eazybytes.eazyschool.config;

import com.eazybytes.eazyschool.handler.CustomAuthenticationFailureHandler;
import com.eazybytes.eazyschool.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
@RequiredArgsConstructor
public class ProjectSecurityConfig {

    private final CustomAuthenticationSuccessHandler success;
    private final CustomAuthenticationFailureHandler failure;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").authenticated() //1. trocamos de permitAll para authenticated
                        .requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg",
                                "/courses", "/about", "/assets/**", "/login/**").permitAll()) //2. qualquer caminho que começe com /login/** será permitido. Spring espera para o login "username" para nome de usuário e "password" para senha do usuário, mas podemos mudar isso
                .formLogin(flc ->
                        flc.loginPage("/login") //3. quando adicionamos um valor aqui no flc com .loginPage() estaremos sobrescrevendo para o Spring, ao invés do login padrão do spring usaremos o nosso.
                                .usernameParameter("customUsername").passwordParameter("customPassword")//4. Spring espera para o login "username" para nome de usuário e "password" para senha do usuário, mas podemos mudar isso com .formLogin(flc -> flc.loginPage("/login").usernameParameter("customUsername").passwordParameter("customPassword") Para debugarmos para ir na classe UserNamePasswordAuthenticationFilter
                                .defaultSuccessUrl("/dashboard")  //5. Podemos também definir uma página padrão para depois que é feito o login lc.loginPage("/login").defaultSuccessUrl("/dashboard")).
                                .failureUrl("/login?error=true").successHandler(success).failureHandler(failure)//
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}EazyBytes@12345").authorities("read").build();
        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$12$88.f6upbBvy0okEa7OfHFuorV29qeK.sVbB9VQ6J6dWM1bW6Qef8m")
                .authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * From Spring Security 6.3 version
     *
     * @return
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }


}
