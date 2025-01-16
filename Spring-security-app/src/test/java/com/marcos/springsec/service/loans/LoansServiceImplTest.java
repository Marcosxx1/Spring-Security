package com.marcos.springsec.service.loans;

import com.marcos.springsec.domain.entity.Loans;
import com.marcos.springsec.repository.loans.LoansRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoansServiceImplTest {

    @MockitoBean
    private LoansRepository loansRepository;

    @Autowired
    private LoansService loansService;

    @Test
    void testFindCustomerLoans_whenCustomerExistsByGivenId_thenReturnListOfLoans(){
        List<Loans> loans = List.of(Loans.builder().build());

        when(loansRepository.findByCustomerIdOrderByStartDtDesc(1L)).thenReturn(Optional.of(loans));

        List<Loans> customerLoans = loansService.findCustomerLoans(1L);

        assert customerLoans.size() == 1;
        assertEquals(loans, customerLoans);
    }

    @Test
    void testFindCustomerLoans_whenCustomerDontExistsByGivenId_thenReturnNull(){

        when(loansRepository.findByCustomerIdOrderByStartDtDesc(1L)).thenReturn(Optional.empty());

        List<Loans> customerLoans = loansService.findCustomerLoans(1L);

        assert customerLoans == null;
    }
}
