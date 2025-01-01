package com.marcos.springsec.controller.cards;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsControllerImpl implements  CardsController{

    @Override
    @GetMapping("/myCards")
    public String getCardDetails() {
        return "Here are the card details from the DB";
    }
}
