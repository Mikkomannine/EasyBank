package com.example.easybankproject.controllers;

import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<BankAccount> createBankAccount(@RequestParam Long userId, @RequestParam String accountNumber) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(BigDecimal.ZERO); // default balance
        bankAccount.setAccountNumber(accountNumber); // unique account number

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return ResponseEntity.ok(savedAccount);
    }
}

