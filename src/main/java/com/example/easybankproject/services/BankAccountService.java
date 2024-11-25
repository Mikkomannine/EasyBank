package com.example.easybankproject.services;

import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.exceptions.UnauthorizedException;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.User;
import com.example.easybankproject.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;

    private UserRepository userRepository;

    private JwtUtil jwtUtil;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public BankAccount getBalance(String token) {
        String username = jwtUtil.extractUsername(token);
        return bankAccountRepository.findByUser(userRepository.findByUsername(username))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public String createBankAccount(BankAccount bankAccount, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UnauthorizedException("Unauthorized: User not found.");
        }

        bankAccount.setUser(user);
        bankAccountRepository.save(bankAccount);

        return "Bank account created with ID: " + bankAccount.getBankAccountId();
    }
    public void createBankAccount(User user) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(BigDecimal.TEN);
        bankAccountRepository.save(bankAccount);
    }
}