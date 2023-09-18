package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.mindhub.homebanking.utils.CardUtils.getCardNumbers;
import static com.mindhub.homebanking.utils.CardUtils.getRandomCvvNumber;
import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;


    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCardsCurrent(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        List<CardDTO> currentCards = client.getCards().stream().map(CardDTO::new).collect(toList());
        return currentCards;
    }


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> registerCard(@RequestParam CardType cardType,
                                               @RequestParam CardColor cardColor,
                                               Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());

        Set<Card> cards = client.getCards();
        if (authentication != null){
            long devitCounter = cards.stream().filter(card -> card.getType() == CardType.DEBIT).count();
            long creditCounter = cards.stream().filter(card -> card.getType() == CardType.CREDIT).count();

            if (devitCounter >= 3 || creditCounter >= 3) {
                return new ResponseEntity<>("403 forbidden", HttpStatus.FORBIDDEN);
            } else {
                if (!cards.stream().filter(card -> card.getType().equals(cardType)).filter(card -> card.getColor().equals(cardColor)).collect(toList()).isEmpty()) {
                    return new ResponseEntity<>("Option selected invalid, you already have a card of this type.", HttpStatus.FORBIDDEN);
                } else {
                    //Creo un numero de tarjeta y verifico que no exita
                    String number1;

                    do{
                        number1 = getCardNumbers();

                    } while (cardRepository.existsByNumber(number1));

                    //Creo un cvv y verifico que no exista
                    int cvv1;

                    do{
                        cvv1 = getRandomCvvNumber(100, 999);

                    }while (cardRepository.existsByCvv(cvv1));


                    Card card = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, number1,cvv1, LocalDateTime.now().plusYears(5), LocalDateTime.now());

                    client.addCard(card);
                    cardService.cardAdd(card);
                    return new ResponseEntity<>("Card created successfully",HttpStatus.CREATED);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    }

