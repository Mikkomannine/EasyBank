package com.example.easybankproject.services;

import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.exceptions.UsernameExists;
import com.example.easybankproject.models.User;
import com.example.easybankproject.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Locale;



@Service
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JwtUtil jwtUtil;

    private NotificationService notificationService;

    private BankAccountService bankAccountService;

    private MessageSource messageSource;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, NotificationService notificationService, BankAccountService bankAccountService, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.notificationService = notificationService;
        this.bankAccountService = bankAccountService;
        this.messageSource = messageSource;
    }

    public User getUserData(String token) {
        String username = jwtUtil.extractUsername(token);
        return userRepository.findByUsername(username);
    }

    public User updateUserData(String token, User updatedUser) {
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        if (user != null) {
            user.setEmail(updatedUser.getEmail());
            user.setAddress(updatedUser.getAddress());
            user.setPhonenumber(updatedUser.getPhonenumber());
            user.setFirstname(updatedUser.getFirstname());
            user.setLastname(updatedUser.getLastname());
            userRepository.save(user);
        }

        return user;
    }

    public String registerUser(User user, Locale locale) throws UsernameExists {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameExists("Username already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        bankAccountService.createBankAccount(user);

        String token = jwtUtil.generateToken(user.getUsername());

        String notificationMessage = messageSource.getMessage("register.notification", null, locale);
        notificationService.createNotification(user, null, user.getUsername() + " " + notificationMessage);

        return token;
    }

    public String loginUser(User user, Locale locale) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser == null || !passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return messageSource.getMessage("error.invalid.credentials", null, "Invalid username or password.", locale);
        }
        String token = jwtUtil.generateToken(user.getUsername());

        String notificationMessage = messageSource.getMessage("login.notification", null, locale);
        notificationService.createNotification(existingUser, null, user.getUsername() + " " + notificationMessage);

        return token;
    }
}
