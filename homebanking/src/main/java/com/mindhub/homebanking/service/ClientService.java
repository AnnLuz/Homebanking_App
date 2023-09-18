package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;


import java.util.List;

public interface ClientService {

    List<ClientDTO> getClientsDTO();
    Client findByEmail(String email);







}
