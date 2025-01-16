package com.marcos.springsec.service.account;

import com.marcos.springsec.domain.entity.Accounts;

import java.util.Optional;

public interface AccountService {

    Accounts findCustomerById(Long id);
}
