package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private String password;
    private Set<AccountDTO> accounts;
    private Set<ClientLoanDTO> loans;

    private Set<CardDTO> cards;

    public ClientDTO(Client client){
        id = client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        password = client.getPassword();
        accounts = client
                .getAccounts()
                .stream()
                .map(element ->new AccountDTO(element))
                .collect(Collectors.toSet());
        loans = client
                .getClientLoans()
                .stream()
                .map(element1 ->new ClientLoanDTO(element1))
                .collect(Collectors.toSet());
        cards = client
                .getCards()
                .stream()
                .map(element2 -> new CardDTO(element2))
                .collect(Collectors.toSet());
    }
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

    public String getPassword() {
        return password;
    }
}
