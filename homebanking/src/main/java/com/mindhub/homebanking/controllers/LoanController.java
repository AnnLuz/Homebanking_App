package com.mindhub.homebanking.controllers;



import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {

    //conecto el repositorio con el controlador
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;





    //obtenemos todos los prestamos
    @GetMapping("/loans")
    public List<LoanDTO> getLoan(){

        return loanService.getLoanDTO();
    }
    @GetMapping("/loans/{id}")
    public LoanDTO getLoans(@PathVariable long id){

        return loanService.getLoansDTO(id);
    }


    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> registerTransaction(@RequestBody LoanAplicationDTO loanAplicationDTO,Authentication authentication){


        if(authentication != null){
            Client client = clientRepository.findByEmail(authentication.getName());
            Loan loan = loanRepository.findLoanById(loanAplicationDTO.getLoanId());
            Account toAccount = accountRepository.findByNumber(loanAplicationDTO.getToAccountNumber());

            //Alguno de los datos no es valido
            if(loanAplicationDTO.getAmount() <= 0.0){
                return new ResponseEntity<>("403 some of the Amount is not valid",HttpStatus.FORBIDDEN);
            }
            if(loanAplicationDTO.getPayments() == 0){
                return new ResponseEntity<>("403 some of the Payment is not valid",HttpStatus.FORBIDDEN);
            }
            if(loanAplicationDTO.getToAccountNumber() == null){
                return new ResponseEntity<>("403 some of the Account is not valid",HttpStatus.FORBIDDEN);
            }
            if(loanAplicationDTO.getAmount() == 0){
                return new ResponseEntity<>("403 some of the Amount is not valid",HttpStatus.FORBIDDEN);
            }
            if(loanAplicationDTO.getLoanId() == 0){
                return new ResponseEntity<>("403 some of the Loan is not valid",HttpStatus.FORBIDDEN);
            }
            //Si la cuenta de destino no existe
            if(toAccount == null){
                return new ResponseEntity<>("403 some of the Account is not valid",HttpStatus.FORBIDDEN);
            }
            //La cuenta destino no pertenece al cliente autenticado
            Set<Account> clientAccounts = client.getAccounts();
            if(!clientAccounts.contains(toAccount)){
                return new ResponseEntity<>("403 if the destination account does not\n" +
                        "belongs to the authenticated client",HttpStatus.FORBIDDEN);
            }
            //Si el prestamo no existe
            if(!loanRepository.existsById(loanAplicationDTO.getLoanId())){
                return new ResponseEntity<>("403 the loan does not exist",HttpStatus.FORBIDDEN);
            }
            //Si el monto solicitado supera el monto maximo permitido del prestamo solicitado
            if(loanAplicationDTO.getAmount() > loan.getMaxAmount() || loanAplicationDTO.getAmount() <= 0){
                return new ResponseEntity<>("403 the amount requested exceeds the maximum amount allowed for the requested loan",HttpStatus.FORBIDDEN);
            }
            //Si la cantidad de cuotas no está disponible para el prestamo solicitado

            if(!loan.getPayments().contains(loanAplicationDTO.getPayments())){
                return new ResponseEntity<>("403 the amount of installments is not available for the requested loan",HttpStatus.FORBIDDEN);
            }

            double totalLoan = (loanAplicationDTO.getAmount() * 0.20) + loanAplicationDTO.getAmount();
            double amount = Math.floor(totalLoan/loanAplicationDTO.getPayments());

            ClientLoan clientLoan = new ClientLoan(totalLoan, loanAplicationDTO.getPayments(), client, loan);
            loan.addClientLoan(clientLoan);
            client.addClientLoan(clientLoan);
            clientLoanRepository.save(clientLoan);

           String description = clientLoan.getLoan().getName() + " Loan Aproved";

            Transaction transactionsLoanCredit = new Transaction(TransactionType.CREDIT, loanAplicationDTO.getAmount(), description, LocalDateTime.now(), toAccount);
            transactionRepository.save(transactionsLoanCredit);

            //Descuentos
            Double auxDestiny =loanAplicationDTO.getAmount() + toAccount.getBalance();

            //Actualización de montos
            toAccount.setBalance(auxDestiny);

            return new ResponseEntity<>("200 applied loan", HttpStatus.CREATED);

        } return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}


