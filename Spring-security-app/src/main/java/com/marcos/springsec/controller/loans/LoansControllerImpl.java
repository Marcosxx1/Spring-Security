package com.marcos.springsec.controller.loans;

import org.springframework.web.bind.annotation.GetMapping;

public class LoansControllerImpl implements LoansController{

    @Override
    @GetMapping("/myLoans")
    public String getLoansDetails() {
        return "Here are the loans from the DB";
    }
}
