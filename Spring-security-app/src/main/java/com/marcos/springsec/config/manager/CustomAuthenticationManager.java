package com.marcos.springsec.config.manager;

import com.marcos.springsec.domain.entity.Customer;
import com.marcos.springsec.exception.exeptions.ResourceNotFoundException;
import com.marcos.springsec.service.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private CustomerService customerService;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Customer customer = customerService.getCustomerByEmail(authentication.getName());
            if (!passwordEncoder.matches(authentication.getCredentials().toString(), customer.getPassword())) {
                throw new BadCredentialsException("Wrong e-mail or password.");
            }
            return new UsernamePasswordAuthenticationToken(authentication.getName(), customer.getPassword());
        } catch (ResourceNotFoundException ex) {
            throw new BadCredentialsException("User not found", ex);
        }
    }



}
