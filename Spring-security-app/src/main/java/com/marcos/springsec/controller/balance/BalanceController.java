package com.marcos.springsec.controller.balance;

import com.marcos.springsec.domain.entity.AccountTransactions;

import java.util.List;

public interface BalanceController {

    List<AccountTransactions> getBalanceDetails(Long id);
}
