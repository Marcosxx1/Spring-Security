package com.marcos.springsec.controller.customer;

import com.marcos.springsec.domain.dto.internal.CustomerRegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.marcos.springsec.constants.PathConstants.CUSTOMER;


@RequestMapping(
        value = CUSTOMER,
        produces = {MediaType.APPLICATION_JSON_VALUE})
public interface CustomerController {

    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(CustomerRegistrationRequest customerRegistrationRequest);
}
