package com.marcos.springsec.controller.account;

import com.marcos.springsec.domain.entity.Accounts;
import com.marcos.springsec.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam Long id) {
        return accountService.findCustomerById(id);
    }
}
