

package com.example.easybankproject.controllers;

import com.example.easybankproject.models.Transaction;
import com.example.easybankproject.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private TransactionService transactionService;

    private MessageSource messageSource;

    @Autowired
    public TransactionController(TransactionService transactionService, MessageSource messageSource) {
        this.transactionService = transactionService;
        this.messageSource = messageSource;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction, @RequestHeader("Accept-Language") Locale locale) {
        String result = transactionService.createTransaction(transaction, locale);
        if (result.startsWith(messageSource.getMessage("created.transaction", null, locale))) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }


    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getTransactions(@RequestHeader("Authorization") String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String jwtToken = token.substring(7);
        List<Transaction> transactions = transactionService.getTransactions(jwtToken);
        return ResponseEntity.ok(transactions);
    }
}
