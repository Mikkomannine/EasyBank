// src/main/java/com/example/easybankproject/controllers/BankAccountController.java
package com.example.easybankproject.controllers;

import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.User;
import com.example.easybankproject.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vaadin.flow.server.VaadinSession;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/bankaccount")
public class BankAccountController {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BankAccountController(UserRepository userRepository, BankAccountRepository bankAccountRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/balance")
    public ResponseEntity<BankAccount> getBalance(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String username = jwtUtil.extractUsername(jwtToken);
        BankAccount ban = bankAccountRepository.findByUser(userRepository.findByUsername(username)).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(ban);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBankAccount(@RequestBody BankAccount bankAccount) {

        String username = (String) VaadinSession.getCurrent().getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: No user found in session.");
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: User not found.");
        }

        bankAccount.setUser(user);
        bankAccountRepository.save(bankAccount);

        return ResponseEntity.ok("Bank account created with ID: " + bankAccount.getBankAccountId());
    }

}