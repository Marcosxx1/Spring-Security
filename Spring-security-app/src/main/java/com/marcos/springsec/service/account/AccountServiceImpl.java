package com.marcos.springsec.service.account;

import com.marcos.springsec.domain.entity.Accounts;
import com.marcos.springsec.exception.ExceptionFactory;
import com.marcos.springsec.repository.account.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountsRepository accountRepository;

    @Override
    public Accounts  findCustomerById(Long id) {
        return accountRepository.findByCustomerId(id).orElse(null);
    }
}
