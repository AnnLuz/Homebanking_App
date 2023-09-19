package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;


    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionService.getTransactions();
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> registerTransaction(Authentication authentication,
                                                      @RequestParam Double amount,
                                                      @RequestParam String description,
                                                      @RequestParam String fromAccountNumber,
                                                      @RequestParam String toAccountNumber){
        if (authentication != null){
            Client client = clientService.findByEmail(authentication.getName());

            Account sourceAccount = accountRepository.findByNumber(fromAccountNumber);
            Account targetAccount = accountRepository.findByNumber(toAccountNumber);

            if(Double.isNaN(amount)){
                return new ResponseEntity<>("403 the amount is empty", HttpStatus.FORBIDDEN);
            }
            if(amount <= 0.0){
                return new ResponseEntity<>("403 some of the Amount is not valid",HttpStatus.FORBIDDEN);
            }
            if(description.isEmpty()){
                return new ResponseEntity<>("403 The description is empty.", HttpStatus.FORBIDDEN);
            }
            if(fromAccountNumber.isEmpty()){
                return new ResponseEntity<>("403 the destination account is empty", HttpStatus.FORBIDDEN);
            }
            if(toAccountNumber.isEmpty()){
                //cuenta o descripcion vacia
                return new ResponseEntity<>("403 the origin account  is empty", HttpStatus.FORBIDDEN);
            }
            if(toAccountNumber.equals(fromAccountNumber)){
                // Cuenta de origen igual a la de destino
                return new ResponseEntity<>("403 source account is equal to destination account", HttpStatus.FORBIDDEN);
            }
            if(targetAccount == null){
                //La cuenta de origen no existe
                return new ResponseEntity<>("403 the origin account does not exist",HttpStatus.FORBIDDEN);
            }

            //verifica si la cuenta pertenece al cliente logueado
            Set<Account> setNumberOrigin = client.getAccounts();
            if(setNumberOrigin.contains(toAccountNumber)){
                return new ResponseEntity<>("403 unauthenticated account",HttpStatus.FORBIDDEN);
            }
            //La cuenta destino no existe
            if (sourceAccount == null){
                return new ResponseEntity<>("403 the destiny account does not exist", HttpStatus.FORBIDDEN);
            }
            //La cuenta de origen no tiene el monto para la transaccion
            if(targetAccount.getBalance() < amount || (amount <= 0)){
                return new ResponseEntity<>("403 insufficient balance", HttpStatus.FORBIDDEN);
            }

            Transaction devitTransaction = new Transaction(TransactionType.DEBIT, -amount, sourceAccount.getNumber() + " " + description, LocalDateTime.now(), sourceAccount);
            Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, targetAccount.getNumber()+" "+ description, LocalDateTime.now(),targetAccount);

            transactionRepository.save(devitTransaction);
            transactionRepository.save(creditTransaction);

            Double auxDestiny = sourceAccount.getBalance()+amount;
            Double auxOrigin = targetAccount.getBalance()-amount;

            targetAccount.setBalance(auxOrigin);
            sourceAccount.setBalance(auxDestiny);

            return new ResponseEntity<>("201 successful transaction",HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
