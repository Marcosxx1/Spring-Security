package com.marcos.springsec.service.customer;

import com.marcos.springsec.domain.dto.internal.CustomerRegistrationRequest;
import com.marcos.springsec.domain.entity.Customer;
import com.marcos.springsec.exception.ExceptionFactory;
import com.marcos.springsec.mapper.customer.CustomerMapper;
import com.marcos.springsec.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Customer getCustomerEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow( ExceptionFactory::resourceNotFoundException);
    }

    @Override
    public String saveCustomer(CustomerRegistrationRequest request) {

        var something = customerRepository.findByEmail(request.getEmail());

        if (something.isPresent()) {
            throw ExceptionFactory.customerAlreadyExistsException(request.getEmail());
        }

        Customer customer = CustomerMapper.toEntity(request);

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        customerRepository.save(customer);

        return "saved successfuly";
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(ExceptionFactory::resourceNotFoundException);
    }
}
