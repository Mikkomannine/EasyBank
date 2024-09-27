package com.example.easybankproject.controllers;


import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.NotificationRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.BankAccount;
import com.example.easybankproject.models.Notification;
import com.example.easybankproject.models.User;
import com.example.easybankproject.utils.JwtUtil;
import org.atmosphere.config.service.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final BankAccountRepository bankAccountRepository;

    private final NotificationRepository notificationRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, BankAccountRepository bankAccountRepository, NotificationRepository notificationRepository, NotificationRepository notificationRepository1) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.bankAccountRepository = bankAccountRepository;
        this.notificationRepository = notificationRepository1;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUserData(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String username = jwtUtil.extractUsername(jwtToken);
        User user = userRepository.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/me")
    public ResponseEntity<User> updateUserData(@RequestHeader("Authorization") String token, @RequestBody User updatedUser) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String username = jwtUtil.extractUsername(jwtToken);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        user.setPhonenumber(updatedUser.getPhonenumber());
        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password
        user.setEmail(user.getEmail());
        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setAddress(user.getAddress());
        user.setPhonenumber(user.getPhonenumber());
        userRepository.save(user);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount.setBalance(BigDecimal.TEN);
        bankAccountRepository.save(bankAccount);

        String token = jwtUtil.generateToken(String.valueOf(user.getUsername()));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setContent(user.getUsername() + " has registered successfully.");
        notification.setTimestamp(LocalDateTime.now());
        notificationRepository.save(notification);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null || !passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }

        String token = jwtUtil.generateToken(String.valueOf(user.getUsername()));

        Notification notification = new Notification();
        notification.setUser(existingUser);
        notification.setContent(user.getUsername() + " has logged in.");
        notification.setTimestamp(LocalDateTime.now());
        notificationRepository.save(notification);
        return ResponseEntity.ok(token);
    }
}
