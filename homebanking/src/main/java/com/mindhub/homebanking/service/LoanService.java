package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    Loan findById(long id);

    LoanDTO getLoansDTO(long id);

    List<LoanDTO> getLoanDTO();



}
