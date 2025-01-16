package com.marcos.springsec.service.loans;

import com.marcos.springsec.domain.entity.Loans;

import java.util.List;

public interface LoansService {
    List<Loans> findCustomerLoans(Long id);
}
