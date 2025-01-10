package com.marcos.springsec.service.userdetails;

import com.marcos.springsec.exception.exeptions.ResourceNotFoundException;
import com.marcos.springsec.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectUserDetailsServiceImpl implements ProjectUserDetailsService{

    private final CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try{
            var customer = customerService.getCustomerEmail(username);
            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
            return new User(customer.getEmail(), customer.getPassword(), grantedAuthorities);
        } catch(ResourceNotFoundException ex){
            throw new UsernameNotFoundException(ex.getMessage());
        }
    }
}
