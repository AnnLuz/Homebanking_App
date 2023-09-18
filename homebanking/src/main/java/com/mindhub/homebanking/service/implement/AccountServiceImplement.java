package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Account findById(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(account->new AccountDTO(account)).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountDTO(long id) {

        return new AccountDTO(this.findById(id));
    }

    @Override
    public Account accountAdd(Account account) {
        return accountRepository.save(account);
    }


}
