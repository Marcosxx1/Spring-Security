package com.marcos.springsec.service.loans;

import com.marcos.springsec.domain.entity.Loans;
import com.marcos.springsec.repository.loans.LoansRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoansServiceImpl implements  LoansService{

    private final LoansRepository loansRepository;

    public List<Loans> findCustomerLoans(Long customerId) {
        return loansRepository.findByCustomerIdOrderByStartDtDesc(customerId).orElse(null);
    }
}
