package com.example.easybankproject.controllers;


import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.NotificationRepository;
import com.example.easybankproject.db.TransactionRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.Notification;
import com.example.easybankproject.models.Transaction;
import com.example.easybankproject.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private TransactionRepository transactionRepository;

    private JwtUtil jwtUtil;

    private UserRepository userRepository;

    private NotificationRepository notificationRepository;

    public TransactionController(BankAccountRepository bankAccountRepository, JwtUtil jwtUtil, UserRepository userRepository, TransactionRepository transactionRepository, NotificationRepository notificationRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.notificationRepository = notificationRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTransction(@RequestBody Transaction transaction) {

        Optional<BankAccount> senderAccount = bankAccountRepository.findByBankAccountId(transaction.getSenderAccountId());
        Optional<BankAccount> receiverAccount = bankAccountRepository.findByBankAccountId(transaction.getReceiverAccountId());
        System.out.println("senderAccount: " + senderAccount);
        System.out.println("receiverAccount " + receiverAccount);
        if (senderAccount.isEmpty() || receiverAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sender or receiver account not found.");
        }
        if (senderAccount.get().getBalance().compareTo(BigDecimal.valueOf(transaction.getAmount())) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds.");
        }
        senderAccount.get().setBalance(senderAccount.get().getBalance().subtract(BigDecimal.valueOf(transaction.getAmount())));
        receiverAccount.get().setBalance(receiverAccount.get().getBalance().add(BigDecimal.valueOf(transaction.getAmount())));
        bankAccountRepository.save(senderAccount.get());
        transaction.setSenderAccountId(transaction.getSenderAccountId());
        transaction.setReceiverAccountId(transaction.getReceiverAccountId());
        transaction.setAmount(transaction.getAmount());
        transaction.setMessage(transaction.getMessage());
        transactionRepository.save(transaction);

        // Create notifications
        Notification senderNotification = new Notification();
        senderNotification.setUser(senderAccount.get().getUser());
        senderNotification.setTransaction(transaction);
        senderNotification.setContent("You sent " + transaction.getAmount() + " to account " + transaction.getReceiverAccountId());
        senderNotification.setTimestamp(LocalDateTime.now());
        notificationRepository.save(senderNotification);

        Notification receiverNotification = new Notification();
        receiverNotification.setUser(receiverAccount.get().getUser());
        receiverNotification.setTransaction(transaction);
        receiverNotification.setContent("You received " + transaction.getAmount() + " from account " + transaction.getSenderAccountId());
        receiverNotification.setTimestamp(LocalDateTime.now());
        notificationRepository.save(receiverNotification);


        return ResponseEntity.ok("Transaction created with ID: " + transaction.getTransactionId());

    }

    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getTransactions(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String username = jwtUtil.extractUsername(jwtToken);
        BankAccount bankAccount = bankAccountRepository.findByUser(userRepository.findByUsername(username)).orElseThrow(() -> new RuntimeException("User not found"));
        List<Transaction> transactions = transactionRepository.findAllBySenderAccountIdOrReceiverAccountId(bankAccount.getBankAccountId(), bankAccount.getBankAccountId());
        return ResponseEntity.ok(transactions);
    }


}