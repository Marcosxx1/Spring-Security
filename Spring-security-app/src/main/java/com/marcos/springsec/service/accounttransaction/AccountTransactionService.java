package com.marcos.springsec.service.accounttransaction;

import com.marcos.springsec.domain.entity.AccountTransactions;

import java.util.List;

public interface AccountTransactionService {

    List<AccountTransactions> findCustomerIdOrdered(Long customerId);
}
