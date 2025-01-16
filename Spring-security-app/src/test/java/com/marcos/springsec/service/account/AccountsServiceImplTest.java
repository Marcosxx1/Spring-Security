package com.marcos.springsec.service.account;

import com.marcos.springsec.domain.entity.Accounts;
import com.marcos.springsec.repository.account.AccountsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountsServiceImplTest {

    @MockitoBean
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountServiceImpl accountsService;


    @Test
    void testGetCustomerById_whenCustomerExists_thenReturnCustomer() {
        Accounts accounts = Accounts.builder().build();
        when(accountsRepository.findById(anyLong())).thenReturn(Optional.of(accounts));

        Accounts result = accountsService.findCustomerById(1L);

        assertEquals(accounts, result);
    }
}
