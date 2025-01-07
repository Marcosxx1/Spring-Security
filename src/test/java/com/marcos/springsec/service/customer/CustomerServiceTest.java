package com.marcos.springsec.service.customer;

import com.marcos.springsec.domain.dto.internal.CustomerRegistrationRequest;
import com.marcos.springsec.domain.entity.Customer;
import com.marcos.springsec.exception.exeptions.CustomerAlreadyExistsException;
import com.marcos.springsec.repository.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {

    @MockitoBean
    private CustomerRepository customerRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerServiceImpl customerService;

    

    @Test
    void testRegisterCustomer_whenUserExists_thenReturnException() {
        Customer customer = Customer.builder().build();
        CustomerRegistrationRequest customerRequest = CustomerRegistrationRequest.builder()
                .email("email")
                .password("password")
                .role("role")
                .build();

         when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(customer));

         when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

         assertThrows(CustomerAlreadyExistsException.class, () -> {
            customerService.saveCustomer(customerRequest);
        });
    }
}
