package com.marcos.springsec.service.cards;

import com.marcos.springsec.domain.entity.Cards;
import com.marcos.springsec.repository.cards.CardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardsServiceImpl implements CardsService {

    private final CardsRepository repository;

    @Override
    public List<Cards> findCardsByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId).orElse(null);
    }
}
