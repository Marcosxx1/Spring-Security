package com.marcos.springsec.controller.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountControllerImpl implements AccountController{


    @Override
    @GetMapping ("/myAccount")
    public String getAccountDetails() {
        return "Here are the account details from the DB";
    }
}
