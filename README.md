# Projeto: Aprendizado de Spring Security

## Índice
- [Introdução ao Projeto](#introdu%C3%A7%C3%A3o-ao-projeto)
- [Configurações de Segurança](#configura%C3%A7%C3%B5es-de-seguran%C3%A7a)
    - [ProjectSecurityConfiguration](#projectsecurityconfiguration)

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

Classe disponível no pacote `com.marcos.springsec.config`.

