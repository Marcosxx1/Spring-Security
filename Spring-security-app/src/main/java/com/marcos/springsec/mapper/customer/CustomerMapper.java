package com.marcos.springsec.mapper.customer;

import com.marcos.springsec.domain.dto.internal.AuthoritiesResponse;
import com.marcos.springsec.domain.dto.internal.CustomerRegistrationRequest;
import com.marcos.springsec.domain.dto.internal.UserResponse;
import com.marcos.springsec.domain.entity.Customer;

import java.util.List;
import java.util.stream.Collectors;

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

    public static UserResponse toResponse(Customer customer) {
        List<AuthoritiesResponse> authoritiesResponse = customer.getAuthorities().stream()
                .map(authority -> AuthoritiesResponse.builder()
                        .name(authority.getName())
                        .build())
                .collect(Collectors.toList());

        return UserResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .role(customer.getRole())
                .authorities(authoritiesResponse)
                .build();
    }
}
