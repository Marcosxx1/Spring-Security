package com.marcos.springsec.service.customer;

import com.marcos.springsec.domain.dto.internal.CustomerRegistrationRequest;
import com.marcos.springsec.domain.dto.internal.UserResponse;
import com.marcos.springsec.domain.entity.Customer;
import com.marcos.springsec.exception.ExceptionFactory;
import com.marcos.springsec.mapper.customer.CustomerMapper;
import com.marcos.springsec.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.marcos.springsec.mapper.customer.CustomerMapper.toResponse;


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

        var user = customerRepository.findByEmail(request.getEmail());

        if (user.isPresent()) {
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

    @Override
    public UserResponse getUserDetailsAfterLogin(Authentication authentication) {
        var user = customerRepository.findByEmail(authentication.getName())
                .orElse(null);

        assert user != null;
        return toResponse(user);
    }
}
