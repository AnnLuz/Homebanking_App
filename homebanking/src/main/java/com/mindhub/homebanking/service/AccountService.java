package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {

    Account findById(long id);

    List<AccountDTO> getAccounts();

    AccountDTO getAccountDTO(long id);

    Account accountAdd(Account account);


}
