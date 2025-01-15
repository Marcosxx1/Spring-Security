package com.marcos.springsec.config;

import com.marcos.springsec.service.userdetails.ProjectUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectUserNamePwdAuthenticationProvider implements AuthenticationProvider {

    private final ProjectUserDetailsService service;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        UserDetails userDetails = service.loadUserByUsername(userName);

        if (passwordEncoder.matches(pwd, userDetails.getPassword())) {
            // Aqui podemos adicionar checks de validação customizadas aliado ao AuthenticationProvider
            return new UsernamePasswordAuthenticationToken(userName, pwd, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("invalid username or passowrd");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)); // Se não tivermos será retornado: No AuthenticationProvider found for org.springframework.security.authentication.UsernamePasswordAuthenticationToken
    }
}
