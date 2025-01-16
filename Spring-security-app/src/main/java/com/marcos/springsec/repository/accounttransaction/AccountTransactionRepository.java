package com.marcos.springsec.repository.accounttransaction;

import com.marcos.springsec.domain.entity.AccountTransactions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountTransactionRepository extends CrudRepository<AccountTransactions, Long>{

     Optional<List<AccountTransactions>> findByCustomerIdOrderByTransactionDtDesc(Long customerId);
}
