package com.marcos.springsec.service.customer;

import com.marcos.springsec.domain.dto.internal.CustomerRegistrationRequest;
import com.marcos.springsec.domain.entity.Customer;

public interface CustomerService {

    Customer getCustomerEmail(String email);
    String saveCustomer(CustomerRegistrationRequest request);
}
