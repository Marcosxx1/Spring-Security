package com.marcos.springsec.controller.loans;

import com.marcos.springsec.domain.entity.Loans;
import com.marcos.springsec.service.loans.LoansService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoansControllerImpl implements LoansController{

    private final LoansService  service;

    @Override
    public List<Loans> getLoansDetails(@RequestParam Long id) {
        return service.findCustomerLoans(id);
    }
}
