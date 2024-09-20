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

    @PostMapping("/create")
    public ResponseEntity<String> createBankAccount(@RequestBody BankAccount bankAccount) {
        // Retrieve the current username from the Vaadin session
        String username = (String) VaadinSession.getCurrent().getAttribute("username");


        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: No user found in session.");
        }

        // Fetch the user from the database
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: User not found.");
        }

        // Associate the bank account with the current user
        bankAccount.setUser(user);

        // Save the bank account to the database
        bankAccountRepository.save(bankAccount);

        return ResponseEntity.ok("Bank account created with ID: " + bankAccount.getBank_account_id());
    }
/*
    @PostMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance() {
        // Retrieve the JWT token from the Vaadin session
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        System.out.println("token: " + token);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Extract the username from the JWT token
        String username = jwtUtil.extractUsername(token);

        // Fetch the user from the database
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Fetch the bank account associated with the user
        BankAccount bankAccount = bankAccountRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        // Return the balance
        return ResponseEntity.ok(bankAccount.getBalance());
    }*/


    @GetMapping("/balance")
    public ResponseEntity<BankAccount> getUserData(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String username = jwtUtil.extractUsername(jwtToken);
        BankAccount ban = bankAccountRepository.findByUser(userRepository.findByUsername(username)).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(ban);
    }
}
/*
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
    public ResponseEntity<BankAccount> createBankAccount(@RequestParam Long userId ) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(BigDecimal.TEN); // default balance

        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return ResponseEntity.ok(savedAccount);
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestParam Long BankaccountID) {
        BankAccount bankAccount = bankAccountRepository.findById(Math.toIntExact(BankaccountID)).
                orElseThrow(() -> new RuntimeException("Account not found"));
        return ResponseEntity.ok(bankAccount.getBalance());
    }

}
*/
/*
import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.User;
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

    @PostMapping("/create")
    public ResponseEntity<String> createBankAccount(@RequestParam BigDecimal initialBalance) {
        // Retrieve the current username from the Vaadin session
        String username = (String) VaadinSession.getCurrent().getAttribute("username");

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: No user found in session.");
        }

        // Fetch the user from the database
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: User not found.");
        }

        // Create a new BankAccount and associate it with the current user
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(initialBalance != null ? initialBalance : BigDecimal.ZERO);

        // Save the bank account to the database
        bankAccountRepository.save(bankAccount);

        return ResponseEntity.ok("Bank account created with ID: " + bankAccount.getBank_account_id());
    }
}

*/