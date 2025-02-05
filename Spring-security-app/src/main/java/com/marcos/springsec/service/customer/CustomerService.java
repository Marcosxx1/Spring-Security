package com.marcos.springsec.service.customer;

import com.marcos.springsec.domain.dto.internal.CustomerRegistrationRequest;
import com.marcos.springsec.domain.dto.internal.UserResponse;
import com.marcos.springsec.domain.entity.Customer;
import org.springframework.security.core.Authentication;

public interface CustomerService {

    Customer getCustomerByEmail(String email);

    String saveCustomer(CustomerRegistrationRequest request);

    Customer getCustomerById(Long id);

    UserResponse getUserDetailsAfterLogin(Authentication authentication);
}
