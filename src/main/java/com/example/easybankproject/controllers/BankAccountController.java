
package com.example.easybankproject.controllers;

import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vaadin.flow.server.VaadinSession;

import java.util.Locale;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@RestController
@RequestMapping("/api/bankaccount")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;



    @GetMapping("/balance")
    public ResponseEntity<BankAccount> getBalance(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            BankAccount bankAccountBalance = bankAccountService.getBalance(jwtToken);
            return ResponseEntity.ok(bankAccountBalance);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            String username = (String) VaadinSession.getCurrent().getAttribute("username");
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: No user found in session.");
            }

            String response = bankAccountService.createBankAccount(bankAccount, username);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}