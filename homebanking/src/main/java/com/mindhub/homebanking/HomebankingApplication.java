package com.mindhub.homebanking;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	/*@Autowired
	PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,CardRepository cardRepository) {
		return (args) -> {

			Client client = new Client(" Morel", "Melba", "melba@mindhub.com",passwordEncoder.encode("123"));
			Client client1 = new Client("Angelo", "Alvez", "angelo@gmail.com",passwordEncoder.encode("456"));
			Client client2 = new Client("Lisi", "Jimp", "lisi@gmail.com",passwordEncoder.encode("789"));
			Client client3 = new Client("Admin", "Admin", "admin@admin.com",passwordEncoder.encode("159"));


			clientRepository.save(client);
			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);


			Account account = new Account("VIN001" , LocalDateTime.now(),546.35);
			Account account1 = new Account("VIN002", LocalDateTime.now().plusDays(1),102.35);
			Account account2 = new Account("VIN003", LocalDateTime.of(2023,8,1,10,34,10),588415.35);
			Account account3 = new Account("VIN0010" , LocalDateTime.now(),7416.35);
			Account account4 = new Account("VIN005" , LocalDateTime.of(2023,7,5,9,34,25),6974565.25);


			client.addAccount(account);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client3.addAccount(account1);
			client.addAccount(account4);



			accountRepository.save(account);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			Transaction transaction = new Transaction(TransactionType.CREDIT,6548.23,"Payment of assets",LocalDateTime.now(),account);
			Transaction transaction1 = new Transaction(TransactionType.DEBIT,-9872.23,"Payment to Accountant",LocalDateTime.now(),account3);
			Transaction transaction2 = new Transaction(TransactionType.DEBIT,-4111.21,"Investments",LocalDateTime.now(),account4);
			Transaction transaction3 = new Transaction(TransactionType.DEBIT,-4111.21,"General expenses",LocalDateTime.now(),account1);
			Transaction transaction4 = new Transaction(TransactionType.DEBIT,-4111.21,"Advance payment 1",LocalDateTime.now(),account2);


			//account.addTransaction(transaction);
			//account3.addTransaction(transaction1);
			//account4.addTransaction(transaction2);
			//account1.addTransaction(transaction3);
			//account2.addTransaction(transaction4);


			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

			Loan loan = new Loan();
			loan.setName("Mortgage");
			loan.setMaxAmount(500000.00);
			loan.setPayments(Set.of(12, 24, 36, 48, 60));

			Loan loan1 = new Loan();
			loan1.setName("Staff");
			loan1.setMaxAmount(100000.00);
			loan1.setPayments(Set.of(6, 12, 24));

			Loan loan2 = new Loan();
			loan2.setName("Automotive");
			loan2.setMaxAmount(300000.00);
			loan2.setPayments(Set.of(6, 12, 24,36));


			loanRepository.save(loan);
			loanRepository.save(loan1);
			loanRepository.save(loan2);

			ClientLoan clientLoan = new ClientLoan();
			ClientLoan clientLoan1 = new ClientLoan();
			ClientLoan clientLoan2 = new ClientLoan();
			ClientLoan clientLoan3 = new ClientLoan();

			clientLoan.setAmount(400000.00);
			clientLoan.setPayments(60);
			clientLoan.setClient(client);
			clientLoan.setLoan(loan1);

			clientLoan3.setAmount(50000.00);
			clientLoan3.setPayments(12);
			clientLoan3.setClient(client);
			clientLoan3.setLoan(loan2);

			clientLoan1.setAmount(100000.00);
			clientLoan1.setPayments(24);
			clientLoan1.setClient(client1);
			clientLoan1.setLoan(loan2);

			clientLoan2.setAmount(200000.00);
			clientLoan2.setPayments(36);
			clientLoan2.setClient(client2);
			clientLoan2.setLoan(loan);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan);

			Card card = new Card();
			card.setClient(client);
			card.setCardHolder("Morel Melba");
			card.setType(CardType.DEBIT);
			card.setColor(CardColor.GOLD);
			card.setNumber("65228574745885688");
			card.setCvv(654);
			card.setThruDate(LocalDateTime.of(2028,8,22,10,15,41));
			card.setFromDate(LocalDateTime.now());

			Card card1 = new Card();
			card1.setClient(client);
			card1.setCardHolder("Morel Melba");
			card1.setType(CardType.DEBIT);
			card1.setColor(CardColor.TITANIUM);
			card1.setNumber("6844711116552200");
			card1.setCvv(671);
			card1.setThruDate(LocalDateTime.of(2028,10,18,9,45,12));
			card1.setFromDate(LocalDateTime.now());

			Card card2 = new Card();
			card2.setClient(client1);
			card2.setCardHolder("Angelo Alvez");
			card2.setType(CardType.CREDIT);
			card2.setColor(CardColor.SILVER);
			card2.setNumber("90004411585456547");
			card2.setCvv(324);
			card2.setThruDate(LocalDateTime.of(2028,8,16,7,55,12));
			card2.setFromDate(LocalDateTime.now());

			client.addCard(card);
			client.addCard(card1);
			client1.addCard(card2);


			cardRepository.save(card);
            cardRepository.save(card1);
			cardRepository.save(card2);













		};

	}*/

}
