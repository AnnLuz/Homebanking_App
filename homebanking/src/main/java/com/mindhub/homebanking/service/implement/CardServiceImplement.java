package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public void cardAdd(Card card) {
        cardRepository.save(card);
    }

    @Override
    public List<CardDTO> currentCards(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getCards().stream().map(CardDTO::new).collect(toList());
    }
}
