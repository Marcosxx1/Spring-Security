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
- [Autenticação utilizando jdbcUserDetailsManager](#autenticação-utilizando-jdbcuserdetailsmanager)
- [implementação de UserDetailsService para lógica de recuperação customizada](#implementação-de-userdetailsservice-para-logica-de-recuperacao-customizada)
- [Fluxo sequencial utilizando nossa propria implementação de UserDetailsService](#fluxo-sequencial-utilizando-nossa-própria-implementação-de-userdetailsservice)
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

    public static void main(String[] args) {
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

class Ex{
    public static void main(String[] args) {

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
    }
}
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

## Autenticação utilizando JdbcUserDetailsManager

Ao invés de criarmos usuários em memória, podemos armazená-los no banco de dados utilizando o `JdbcUserDetailsManager`. Isso nos permite gerenciar credenciais e permissões diretamente no banco, de forma mais escalável.

### Configuração básica
Para usar o `JdbcUserDetailsManager`, criamos um bean no arquivo de configuração:

```java
@Bean
public UserDetailsService userDetailsService(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
}
```

Com essa configuração, o Spring Security espera que o banco de dados contenha as tabelas necessárias para armazenar usuários e suas autoridades (permissões). Essas tabelas podem ser criadas manualmente ou geradas automaticamente com base no esquema esperado pelo `JdbcUserDetailsManager`.

### Esquema de tabelas esperado
Por padrão, o `JdbcUserDetailsManager` utiliza as tabelas `users` e `authorities` com as seguintes definições básicas:

#### Tabela `users`
```sql
CREATE TABLE users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL
);
```

#### Tabela `authorities`
```sql
CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);
```

### Inserção de dados no banco de dados
Após criar as tabelas, inserimos os usuários e suas autoridades manualmente ou via script. Por exemplo:

```sql
-- Inserir usuário
INSERT INTO users (username, password, enabled) VALUES ('user1', '{bcrypt}$2a$10$Wq7gJnVxzP1uIzDjH7VbAe3RLefpssRV18y6qFZHD9BoqVQ44.sni', true);

-- Inserir autoridade
INSERT INTO authorities (username, authority) VALUES ('user1', 'ROLE_USER');
```

O campo `password` deve conter uma senha codificada (ex.: BCrypt). Podemos usar um `PasswordEncoder` para gerar essas senhas no Java.

---

## UserDetailsService implementation para lógica de recuperação customizada

Quando precisamos carregar detalhes de usuários com base em nossas próprias tabelas, colunas ou lógica, devemos criar uma implementação customizada de `UserDetailsService`. Isso é útil para integrar o Spring Security com um modelo de dados específico do negócio.

### Interface personalizada
Criamos uma interface que estenda `UserDetailsService`:

```java
public interface ProjectUserDetailsService extends UserDetailsService {
}
```

### Implementação customizada
Em seguida, implementamos a lógica personalizada no método `loadUserByUsername`:

```java
@Service
@RequiredArgsConstructor
public class ProjectUserDetailsServiceImpl implements ProjectUserDetailsService {

    private final CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar o cliente no banco de dados usando um serviço específico
        var customer = customerService.getCustomerEmail(username);

        // Converter o papel do cliente em GrantedAuthority
        List<GrantedAuthority> grantedAuthorities = List.of(
            new SimpleGrantedAuthority(customer.getRole())
        );

        // Retornar o objeto User do Spring Security
        return new User(
            customer.getEmail(),
            customer.getPassword(),
            grantedAuthorities
        );
    }
}
```

### Integração com o banco de dados
A lógica de busca é delegada ao `CustomerService`, que se comunica diretamente com o repositório:

```java
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomerEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("Usuário não encontrado com email: " + email)
        );
    }
}
```

### Exemplo de esquema para uma tabela customizada
Caso utilizemos uma tabela personalizada, ela pode ser estruturada assim:

```sql
CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
);
```
## Fluxo Sequencial utilizando nossa Própria Implementação de UserDetailsService

### Passo a Passo do Fluxo de Autenticação

1. **Usuário tenta acessar a página segura**\
   O usuário tenta acessar um recurso protegido na aplicação, como uma página que exige autenticação.

2. **Filtros do Spring identificam que o usuário não está logado**\
   Filtros como `AuthorizationFilter`, `AbstractAuthenticationProcessingFilter` e `DefaultLoginPageGeneratingFilter` verificam que o usuário não possui uma sessão ativa e o redirecionam para a página de login.

3. **Usuário envia suas credenciais**\
   O usuário insere o nome de usuário e senha e submete a requisição de login, que é interceptada pelos filtros de autenticação do Spring Security.

4. **Requisição interceptada e credenciais processadas**\
   Filtros como `UsernamePasswordAuthenticationFilter` extraem o nome de usuário e senha da requisição e criam um objeto `UsernamePasswordAuthenticationToken`. Esse objeto é uma implementação da interface `Authentication`.

5. **ProviderManager autentica o token**\
   O `ProviderManager`, que é uma implementação de `AuthenticationManager`, verifica qual provedor de autenticação suporta o tipo do token criado. Normalmente, o método `authenticate()` do `DaoAuthenticationProvider` é invocado.

6. **Carregamento de detalhes do usuário**\
   O `DaoAuthenticationProvider` chama o método `loadUserByUsername` da implementação customizada de `UserDetailsService` (como `ProjectUserDetailsServiceImpl`) para carregar os detalhes do usuário a partir do banco de dados ou outra fonte.

    - **Exemplo de implementação:**
      ```java
      @Service
      @RequiredArgsConstructor
      public class ProjectUserDetailsServiceImpl implements UserDetailsService {
 
          private final CustomerService customerService;
 
          @Override
          public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
              var customer = customerService.getCustomerEmail(username);
              List<GrantedAuthority> authorities = List.of(
                  new SimpleGrantedAuthority(customer.getRole())
              );
              return new User(customer.getEmail(), customer.getPassword(), authorities);
          }
      }
      ```

7. **Validação das credenciais**\
   Após carregar os detalhes do usuário, o `DaoAuthenticationProvider` utiliza um `PasswordEncoder` para comparar a senha informada com a senha armazenada. Se as senhas coincidirem, a autenticação é considerada bem-sucedida.

8. **Objeto de autenticação retornado**\
   Um objeto `Authentication` contendo os detalhes do sucesso ou falha da autenticação é retornado para o `ProviderManager`.

9. **Resultado da autenticação avaliado pelo ProviderManager**\
   O `ProviderManager` verifica se a autenticação foi bem-sucedida:

    - Se não for, tenta outros provedores de autenticação disponíveis.
    - Caso contrário, retorna os detalhes de autenticação para os filtros.

10. **Sessão autenticada armazenada**\
    O objeto `Authentication` é armazenado no `SecurityContext` pelo filtro apropriado para uso futuro na aplicação. Uma resposta apropriada (como redirecionamento ou exibição de conteúdo) é enviada ao usuário.

