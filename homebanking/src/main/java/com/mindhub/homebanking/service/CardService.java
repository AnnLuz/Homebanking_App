package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardService {

    void  cardAdd(Card card);

    List<CardDTO> currentCards(Authentication authentication);
}
