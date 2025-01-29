package com.marcos.springsec.repository.account;

import com.marcos.springsec.domain.entity.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long>{

    Optional<Accounts> findByCustomerId(Long customerId);
}
