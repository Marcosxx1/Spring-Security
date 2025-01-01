package com.marcos.springsec.controller.balance;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceControllerImpl implements BalanceController{


    @Override
    @GetMapping("/myBallance")
    public String getBalanceDetails() {
        return "Here are the balance details from the DB";
    }
}
