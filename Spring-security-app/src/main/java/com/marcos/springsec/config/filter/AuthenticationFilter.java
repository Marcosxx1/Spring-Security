package com.marcos.springsec.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcos.springsec.config.manager.CustomAuthenticationManager;
import com.marcos.springsec.domain.dto.internal.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/*Quando um usuário acessar /login , o primeiro método que será invocado é attemptAuthentication
 * quando a request chegar aqui, precisamos extrair o body da request
 *  utilizamos o request.getInputStrem para deserializar no objeto que será utilizado
 *
 * Os filtros do spring vem antes do do DispatcherServlet, logo, quando disparamos uma exception dentro de um filtro
 * é impossível lidar com ela dentro do @ControllerAdvice
 *
 * O dispatchersERVLET Está no centro do recebimento da request invocando o controlador handler method e mandando uma
 * resposta de volta.
 *
 * Funcionaria assim:
 * DispatcherServlet -> Controller -> (request) -> Exception Handlers > DispatcherServlet
 * */

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private CustomAuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            return authenticationManager.authenticate(authentication); //

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication
    ) {
        System.out.println("AUTENTICAÇÃO FUNCIONOU VÉI!");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        System.out.println("NÃO FUNCIONOU VÉI!");

    }
}
