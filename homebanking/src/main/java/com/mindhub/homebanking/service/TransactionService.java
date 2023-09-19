package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.TransactionDTO;

import javax.transaction.Transaction;
import java.util.List;

public interface TransactionService {

    List<TransactionDTO> getTransactions();

}
