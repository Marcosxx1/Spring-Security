package com.marcos.springsec.mapper.customer;

import com.marcos.springsec.domain.dto.internal.CustomerRegistrationRequest;
import com.marcos.springsec.domain.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerRegistrationRequest customerRegistrationRequest) {
        return Customer.builder()
                .name(customerRegistrationRequest.getName())
                .email(customerRegistrationRequest.getEmail())
                .mobileNumber(customerRegistrationRequest.getMobileNumber())
                .password(customerRegistrationRequest.getPassword())
                .role(customerRegistrationRequest.getRole())
                .build();
    }
}
