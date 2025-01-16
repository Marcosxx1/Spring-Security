package com.marcos.springsec.controller.balance;

import com.marcos.springsec.domain.entity.AccountTransactions;
import com.marcos.springsec.service.accounttransaction.AccountTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BalanceControllerImpl implements BalanceController{

    private final AccountTransactionService service;

    @Override
    @GetMapping("/myBallance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam  Long id) {
        return service.findCustomerIdOrdered(id);
    }
}
