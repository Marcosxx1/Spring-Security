package com.marcos.springsec.mapper.customer;

import com.marcos.springsec.domain.dto.internal.CustomerRegistrationRequest;
import com.marcos.springsec.domain.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerRegistrationRequest customerRegistrationRequest) {
        return Customer.builder()
                .email(customerRegistrationRequest.getEmail())
                .password(customerRegistrationRequest.getPassword())
                .role(customerRegistrationRequest.getRole())
                .build();
    }
}
