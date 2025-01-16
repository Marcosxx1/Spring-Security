package com.marcos.springsec.service.accounttransaction;

import com.marcos.springsec.domain.entity.AccountTransactions;
import com.marcos.springsec.repository.accounttransaction.AccountTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTransactionServiceImpl implements AccountTransactionService{

    private final AccountTransactionRepository accountTransactionRepository;

    @Override
    public List<AccountTransactions> findCustomerIdOrdered(Long customerId) {
        return accountTransactionRepository.findByCustomerIdOrderByTransactionDtDesc(customerId).orElse(null);
    }
}
