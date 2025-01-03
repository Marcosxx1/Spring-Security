package com.marcos.springsec.config;

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
    private final String[] ALLOWED_PATHS = {CONTACT, NOTICES, ERROR,CUSTOMER};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); // Precisamos desabilitar o csrf para poder fazer requisições que não sejam GET para rotas desprotegidas
        http.authorizeHttpRequests((request) -> request
                .requestMatchers(AUTHENTICATED_PATHS).authenticated()
                .requestMatchers(ALLOWED_PATHS).permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



    /**
     * O método `userDetailsService(DataSource dataSource)` é comentado nesta configuração
     * porque já utilizamos uma implementação personalizada de {@link org.springframework.security.core.userdetails.UserDetailsService}.
     * <p>
     * Essa decisão foi tomada para evitar conflitos com a configuração automática do Spring Security,
     * que criaria um `JdbcUserDetailsManager` baseado em um esquema/tabela pré-definida.
     * <p>
     * Ao usar uma implementação personalizada como {@link com.marcos.springsec.service.userdetails.ProjectUserDetailsServiceImpl},
     * temos as seguintes vantagens:
     *
     * <ul>
     *   <li><strong>Evitar conflitos:</strong> O Spring Security não permite múltiplos beans de `UserDetailsService` sem configuração explícita.
     *   Comentamos este método para garantir que o Spring use a implementação personalizada.</li>
     *   <li><strong>Controle personalizado:</strong> Podemos adaptar a lógica de autenticação e autorização às necessidades específicas da aplicação,
     *   utilizando as nossas entidades e serviços, como buscar clientes via {@link com.marcos.springsec.service.customer.CustomerService}.</li>
     *   <li><strong>Coerência:</strong> Centralizamos a lógica de autenticação em uma única implementação,
     *   evitando redundância ou inconsistências na configuração.</li>
     * </ul>
     * <p>
     * Caso desejássemos utilizar a abordagem padrão baseada no banco de dados,
     * o método poderia ser ativado novamente, mas isso exigiria um esquema/tabela compatível com as expectativas do `JdbcUserDetailsManager`.
     *
     * @param dataSource Fonte de dados usada para conectar ao banco de dados caso o método fosse utilizado.
     * @return Um {@link org.springframework.security.core.userdetails.UserDetailsService} que utiliza o banco de dados para autenticação.
     */
    public void explicacao() {};

/*    @Bean
    UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    } // nos forçará a utilizar um esquema /tabela pré definida.*/

    /* COMENTADO POIS JÁ TEMOS UM @Bean do UserDetailServices
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}12345").authorities("read").build();
        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$DTzG57.5qOiRdyQrtelWx.Vu5ySjiC8Nd6b2Hp.IzsMtNVasd6prW").authorities("admin").build();

        return new InMemoryUserDetailsManager(user, admin);
    }*/

}
