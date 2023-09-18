package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.AccountUtils.getRandomNumber;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  AccountService accountService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){

        return accountService.getAccounts();
    }
    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id){
        return accountService.getAccountDTO(id);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getCurrentAccount(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getAccounts().stream().map(AccountDTO::new).collect(toList());
    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Account> registerAccount(Authentication authentication){

        if (authentication != null){
            Client client = clientService.findByEmail(authentication.getName());
            Account account;

            if (client.getAccounts().size()>=3){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            }else {
                int accountNumber = getRandomNumber(10000000, 99999999);
                String numAccount;

                do{
                    numAccount = Integer.toString(accountNumber);

                }while(accountRepository.existsByNumber(numAccount));

                account = new Account(("VIN-" + accountNumber), LocalDateTime.now(), 0d);
                client.addAccount(account);
                accountService.accountAdd(account);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
