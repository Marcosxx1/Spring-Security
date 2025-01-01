# Projeto: Aprendizado de Spring Security

## Índice
- [Introdução ao Projeto](#introdução-ao-projeto)
- [Configurações de Segurança](#configurações-de-segurança)
    - [ProjectSecurityConfiguration](#projectsecurityconfiguration)
    - [Gerenciamento de Usuários](#gerenciamento-de-usuários)
    - [Verificação de Senhas Comprometidas](#verificação-de-senhas-comprometidas)
    - [Hierarquia de Interfaces de Usuário](#hierarquia-de-interfaces-de-usuário)

---

## Introdução ao Projeto

(WIP) Este projeto foi criado com o objetivo de aprender e praticar o uso de Spring Security, desenvolvimento de backend com Spring Boot e frontend com Angular. Além disso, o projeto serve como referência para reutilizar trechos de código documentados.

---

## Configurações de Segurança

### ProjectSecurityConfiguration

`ProjectSecurityConfiguration` é a classe principal de configurações de segurança do projeto.

Por padrão, o Spring Security protege todas as rotas de uma aplicação web. Esse comportamento é gerenciado pelo método `defaultSecurityFilterChain(HttpSecurity httpSecurity)` da classe `SpringBootWebSecurityConfiguration`. No entanto, podemos personalizar esse comportamento para permitir ou restringir o acesso a diferentes endpoints. A implementação dessa personalização está na classe `ProjectSecurityConfiguration`.

#### Funcionalidades Principais

- Rotas definidas em `AUTHENTICATED_PATHS` exigem autenticação.
- Rotas definidas em `ALLOWED_PATHS` são acessíveis a todos os usuários sem autenticação.
- Suporte para:
    - Login baseado em formulário.
    - Autenticação HTTP Básica.

#### Anotação de Configuração

A classe utiliza a anotação `@Configuration`, que indica que define um ou mais beans de configuração gerenciados pelo container do Spring.

#### Configuração da Cadeia de Filtros de Segurança

```java
@Configuration
public class ProjectSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(withDefaults())
            .httpBasic(withDefaults());
        
        // Configurações adicionais podem ser incluídas aqui.
        return http.build();
    }
}
```

#### Detalhes do Login

- **Form Login:**
    - Ativado com `http.formLogin(withDefaults());`.
    - Extração de credenciais é feita pela classe `UsernamePasswordAuthenticationFilter` no método `Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)`.

- **HTTP Basic:**
    - Ativado com `http.httpBasic(withDefaults());`.
    - Processado pela classe `BasicAuthenticationFilter` no método `doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)`.

> Nota: Sempre que o `formLogin` ou `httpBasic` forem desativados, utilize lambdas ou Method References para desativação, como em `http.formLogin(AbstractHttpConfigurer::disable)`. A chamada direta ao método `.disable()` está depreciada.

---

### Gerenciamento de Usuários

No Spring Security, é possível definir múltiplos usuários em memória para testes, utilizando `InMemoryUserDetailsManager` e a classe `UserDetails`. Este recurso é recomendado apenas para fins de desenvolvimento ou teste e não deve ser utilizado em produção.

#### Exemplo de Implementação

```java
@Bean
public InMemoryUserDetailsManager userDetailsManager() {
    UserDetails user1 = User.withDefaultPasswordEncoder()
            .username("user1")
            .password("password1")
            .roles("USER")
            .build();

    UserDetails user2 = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("password2")
            .roles("ADMIN")
            .build();

    return new InMemoryUserDetailsManager(user1, user2);
}
```

---

### Verificação de Senhas Comprometidas

O Spring Security 6.3 introduziu o `CompromisedPasswordChecker`, que permite verificar se uma senha foi comprometida em violações de dados conhecidas. Essa funcionalidade é uma camada extra de segurança.

#### Exemplo de Configuração

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

@Bean
public CompromisedPasswordChecker passwordChecker() {
    return new CompromisedPasswordChecker();
}
```

---

### Hierarquia de Interfaces de Usuário

A representação de um usuário no Spring Security é feita através da interface `UserDetails`. Existem diferentes interfaces relacionadas para gerenciamento de usuários:

- **`UserDetailsService`**: Interface básica para carregar detalhes de um usuário.
    - Método principal: `UserDetails loadUserByUsername(String username)`.

- **`UserDetailsManager`**: Extensão de `UserDetailsService` com métodos adicionais para gerenciamento de usuários.
    - Principais métodos:
        - `void createUser(UserDetails user)`
        - `void updateUser(UserDetails user)`
        - `void deleteUser(String username)`
        - `void changePassword(String oldPassword, String newPassword)`
        - `boolean userExists(String username)`

- **Implementações Padrão**:
    1. `InMemoryUserDetailsManager`
    2. `JdbcUserDetailsManager`
    3. `LdapUserDetailsManager`

---

Classe disponível no pacote `com.marcos.springsec.config`. 

