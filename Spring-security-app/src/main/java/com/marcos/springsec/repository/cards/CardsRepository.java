package com.marcos.springsec.repository.cards;

import com.marcos.springsec.domain.entity.Cards;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CardsRepository extends CrudRepository<Cards, Long> {
    Optional<List<Cards>> findByCustomerId(Long customerId);
}
