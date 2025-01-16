package com.marcos.springsec.service.cards;

import com.marcos.springsec.domain.entity.Cards;
import com.marcos.springsec.repository.cards.CardsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CardsServiceImplTest {

    @MockitoBean
    private CardsRepository repository;

    @Autowired
    private CardsService service;
    @Autowired
    private CardsRepository cardsRepository;
    @Autowired
    private CardsService cardsService;


    @Test
    void findCustomerByIdTest_whenCardsExist_thenReturnListOfCardsCustomer(){
        List<Cards> actual = List.of(Cards.builder().build());
        when(cardsRepository.findByCustomerId(anyLong())).thenReturn(Optional.of(actual));

        List<Cards> expected = cardsService.findCardsByCustomerId(1L);

        assertEquals(expected, actual);
    }

    @Test
    void findCustomerByIdTest_whenCardsDontExist_thenReturnNull(){
        when(cardsRepository.findByCustomerId(anyLong())).thenReturn(Optional.empty());

        List<Cards> expected = cardsService.findCardsByCustomerId(1L);

        assertEquals(expected, null);
    }

}
