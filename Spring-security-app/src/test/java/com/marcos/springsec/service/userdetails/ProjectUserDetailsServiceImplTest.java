package com.marcos.springsec.service.userdetails;

import com.marcos.springsec.domain.entity.Authorities;
import com.marcos.springsec.domain.entity.Customer;
import com.marcos.springsec.exception.exeptions.ResourceNotFoundException;
import com.marcos.springsec.service.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectUserDetailsServiceImplTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private ProjectUserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_whenValidUser_thenReturnsUserDetails() {
        // Arrange
        String email = "user@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword("hashedPassword");
        Authorities authority = new Authorities();
        authority.setName("ROLE_USER");
        customer.setAuthorities(List.of(authority));

        when(customerService.getCustomerByEmail(email)).thenReturn(customer);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
        assertEquals("hashedPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_whenUserNotFound_thenThrowsException() {
        String email = "nonexistent@example.com";
        when(customerService.getCustomerByEmail(email))
                .thenThrow(new ResourceNotFoundException("User not found", "User not found"));

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email));
    }

    @Test
    void loadUserByUsername_whenNoAuthorities_thenReturnsUserWithEmptyAuthorities() {
        String email = "user@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword("hashedPassword");
        customer.setAuthorities(Collections.emptyList());

        when(customerService.getCustomerByEmail(email)).thenReturn(customer);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertTrue(userDetails.getAuthorities().isEmpty());
    }
}
