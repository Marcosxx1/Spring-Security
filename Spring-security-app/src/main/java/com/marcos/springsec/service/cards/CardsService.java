package com.marcos.springsec.service.cards;

import com.marcos.springsec.domain.entity.Cards;

import java.util.List;

public interface CardsService {

    List<Cards> findCardsByCustomerId(Long customerId);
}
