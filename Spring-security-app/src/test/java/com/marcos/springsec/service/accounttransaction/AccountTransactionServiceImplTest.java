package com.marcos.springsec.service.accounttransaction;

import com.marcos.springsec.domain.entity.AccountTransactions;
import com.marcos.springsec.repository.accounttransaction.AccountTransactionRepository;
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
public class AccountTransactionServiceImplTest {

    @MockitoBean
    private AccountTransactionRepository accountTransactionRepository;

    @Autowired
    private AccountTransactionService accountTransactionService;

    @Test
    void testFindCustomerIdOrdered_whenCustomerExistis_thenReturnListOfAccountTransactions() {
        List<AccountTransactions> accountTransactions = List.of(AccountTransactions.builder().build());

        when(accountTransactionRepository.findByCustomerIdOrderByTransactionDtDesc(1L)).thenReturn(Optional.of(accountTransactions));

        List<AccountTransactions> result = accountTransactionService.findCustomerIdOrdered(1L);
        assert result.size() == 1;
        assertEquals(accountTransactions,  result);
    }

    @Test
    void testFindCustomerIdOrdered_whenCustomerDontExistis_thenReturnNull() {
        when(accountTransactionRepository.findByCustomerIdOrderByTransactionDtDesc(1L)).thenReturn(Optional.empty());

        List<AccountTransactions> result = accountTransactionService.findCustomerIdOrdered(1L);
        assert result == null;
    }
}


