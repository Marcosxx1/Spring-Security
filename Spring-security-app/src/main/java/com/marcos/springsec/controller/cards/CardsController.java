package com.marcos.springsec.controller.cards;

import com.marcos.springsec.domain.entity.Cards;

import java.util.List;

public interface CardsController {

    List<Cards> getCardDetails(Long customerId);
}
