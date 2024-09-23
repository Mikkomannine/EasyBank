package com.example.easybankproject.controllers;


import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.TransactionRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.Transaction;
import com.example.easybankproject.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private TransactionRepository transactionRepository;

    private JwtUtil jwtUtil;

    private UserRepository userRepository;

    public TransactionController(BankAccountRepository bankAccountRepository, JwtUtil jwtUtil, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTransction(@RequestBody Transaction transaction) {

        Optional<BankAccount> senderAccount = bankAccountRepository.findByBankAccountId(transaction.getSender_account_id());
        Optional<BankAccount> receiverAccount = bankAccountRepository.findByBankAccountId(transaction.getReceiver_account_id());
        if (senderAccount.isEmpty() || receiverAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sender or receiver account not found.");
        }
        if (senderAccount.get().getBalance().compareTo(BigDecimal.valueOf(transaction.getAmount())) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds.");
        }
        senderAccount.get().setBalance(senderAccount.get().getBalance().subtract(BigDecimal.valueOf(transaction.getAmount())));
        receiverAccount.get().setBalance(receiverAccount.get().getBalance().add(BigDecimal.valueOf(transaction.getAmount())));
        bankAccountRepository.save(senderAccount.get());
        transaction.setSender_account_id(transaction.getSender_account_id());
        transaction.setReceiver_account_id(transaction.getReceiver_account_id());
        transaction.setAmount(transaction.getAmount());
        transaction.setMessage(transaction.getMessage());
        transactionRepository.save(transaction);
        return ResponseEntity.ok("Transaction created with ID: " + transaction.getTransaction_id());

    }

}