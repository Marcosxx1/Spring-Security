# Projeto: Aprendizado de Spring Security

## Índice
- [Introdução ao Projeto](#introdução-ao-projeto)
- [Configurações de Segurança](#configurações-de-segurança)
    - [ProjectSecurityConfiguration](#projectsecurityconfiguration)
    - [Gerenciamento de Usuários](#gerenciamento-de-usuários)
    - [Verificação de Senhas Comprometidas](#verificação-de-senhas-comprometidas)
    - [Hierarquia de Interfaces de Usuário](#hierarquia-de-interfaces-de-usuário)
- [](#userdetails-e-authentication-relação-entre-elas)
  - [Estrutura de herança](#estrutura-de-herança)
    - [Métodos importantes de UserNamePasswordAuthenticationToken](#métodos-importantes-de-usernamepasswordauthenticationtoken)
      - [Papel do Authentication](#papel-do-authentication)
  - [UserDetails](#userdetails)
    - [Papel do UserDetails](#papel-do-userdetails)
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


Classe disponível no pacote `com.marcos.springsec.config`. 

---
 
## UserDetails e Authentication: Relação entre Elas
![img.png](img.png) (*cortesia de eazybytes*)
### Estrutura de Herança
A relação entre `UserDetails` e `Authentication` é essencial no Spring Security para a autenticação e autorização de usuários.
`UserDetails` representa os dados do usuário, como nome, senha e permissões, carregados de um sistema de armazenamento por meio
de serviços como `UserDetailsService`.

Já `Authentication` encapsula o estado da autenticação, verificando se as credenciais fornecidas são válidas e garantindo
acesso controlado aos recursos. O processo conecta ambas as interfaces quando o `AuthenticationManager` ou `AuthenticationProvider`
usa instâncias de `UserDetails` para criar tokens de autenticação (`Authentication`), validando os dados do usuário e definindo
as permissões adequadas.

- **Principal (Interface)**
    - **Authentication (Interface)**
        - **UsernamePasswordAuthenticationToken (Class)**

### Métodos Importantes de `UsernamePasswordAuthenticationToken`
```java
class Ex{

    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken("user", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));

System.out.println(authenticationToken.getName()); // Retorna o nome do principal
System.out.println(authenticationToken.getPrincipal()); // Retorna o objeto principal (geralmente UserDetails)
System.out.println(authenticationToken.getAuthorities()); // Retorna as autoridades (roles)
System.out.println(authenticationToken.getCredentials()); // Retorna as credenciais (ex.: senha)
System.out.println(authenticationToken.getDetails()); // Detalhes adicionais (como IP)
System.out.println(authenticationToken.isAuthenticated()); // Verifica se está autenticado

// Configurações adicionais
authenticationToken.setAuthenticated(true);
authenticationToken.eraseCredentials(); // Remove informações sensíveis como senha

}
```

### Papel do `Authentication`
- **`Authentication`** é usado para determinar se a autenticação foi bem-sucedida ou não.
- Componente essencial para:
    - **`AuthenticationProvider`**: Realiza a autenticação.
    - **`AuthenticationManager`**: Coordena o processo de autenticação.

Exemplo de `AuthenticationManager`:
```java
@Autowired
private AuthenticationManager authenticationManager;

public Authentication authenticateUser(String username, String password) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
    return authenticationManager.authenticate(token);
}
```

---

## UserDetails

### Estrutura de Herança
A interface `UserDetails` é usada para representar as informações de um usuário no sistema.
- **UserDetails (Interface)**
    - **User (Class)**

### Métodos Importantes de `User`
```java
UserDetails user = User.builder()
        .username("john_doe")
        .password(new BCryptPasswordEncoder().encode("password"))
        .roles("USER")
        .build();

System.out.println(user.getUsername()); // Nome de usuário
System.out.println(user.getPassword()); // Senha codificada
System.out.println(user.getAuthorities()); // Autoridades atribuídas ao usuário
System.out.println(user.isAccountNonExpired()); // Verifica se a conta está expirada
System.out.println(user.isAccountNonLocked()); // Verifica se a conta está bloqueada
System.out.println(user.isCredentialsNonExpired()); // Verifica se as credenciais estão expiradas
System.out.println(user.isEnabled()); // Verifica se o usuário está habilitado
```

### Papel do `UserDetails`
- **`UserDetails`** é usado para carregar informações de usuários de sistemas de armazenamento, como banco de dados ou memória.
- Componente essencial para:
    - **`UserDetailsService`**: Carrega informações do usuário com base no nome de usuário.
      ```java
      @Service
      public class CustomUserDetailsService implements UserDetailsService {
  
          @Override
          public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
              if (username.equals("john_doe")) {
                  return User.builder()
                          .username("john_doe")
                          .password(new BCryptPasswordEncoder().encode("password"))
                          .roles("USER")
                          .build();
              }
              throw new UsernameNotFoundException("Usuário não encontrado");
          }
      }
      ```
    - **`UserDetailsManager`**: Adiciona funcionalidade para gerenciar usuários (criar, atualizar, excluir).

### Comportamento Padrão
Caso não haja um `AuthenticationProvider` configurado, o framework usará o **`DaoAuthenticationProvider`** por padrão, que depende de um `UserDetailsService` para carregar os detalhes do usuário.

Exemplo de configuração padrão:
```java
@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("user")
                        .password(new BCryptPasswordEncoder().encode("password"))
                        .roles("USER")
                        .build()
        );
    }
}
```
