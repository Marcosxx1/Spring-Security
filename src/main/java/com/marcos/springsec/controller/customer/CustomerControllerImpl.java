package com.marcos.springsec.controller.customer;

import com.marcos.springsec.domain.dto.internal.CustomerRegistrationRequest;
import com.marcos.springsec.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerControllerImpl implements CustomerController {

    private final CustomerService userService;

    @Override
    @PostMapping
    public String registerUser(@Valid @RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        return userService.saveCustomer(customerRegistrationRequest);
    }
}
