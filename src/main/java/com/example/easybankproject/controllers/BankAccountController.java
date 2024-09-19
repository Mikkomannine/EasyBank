package com.example.easybankproject.controllers;
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

        return ResponseEntity.ok("Bank account created with ID: " + bankAccount.getBankaccountID());
    }
}

