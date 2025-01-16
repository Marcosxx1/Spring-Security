package com.marcos.springsec.controller.loans;

import com.marcos.springsec.domain.entity.Loans;

import java.util.List;

public interface LoansController {

    List<Loans> getLoansDetails(Long id);
}
