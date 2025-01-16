package com.marcos.springsec.controller.cards;

import com.marcos.springsec.domain.entity.Cards;
import com.marcos.springsec.service.cards.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardsControllerImpl implements  CardsController{

    private final CardsService cardsService;

    @Override
    @GetMapping("/myCards")
    public List<Cards> getCardDetails(Long customerId) {
        return cardsService.findCardsByCustomerId(customerId);
    }
}
