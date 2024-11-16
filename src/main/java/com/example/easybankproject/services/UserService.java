package com.example.easybankproject.services;
/*
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.User;
import com.example.easybankproject.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.Locale;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private MessageSource messageSource;  // Inject MessageSource for localization

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

    public String registerUser(User user, Locale locale) {
        // Use a localized message if the username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return messageSource.getMessage("error.username.exists", null, locale);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        // Create a bank account for the user
        bankAccountService.createBankAccount(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());

        // Send a localized registration notification
        String notificationMessage = messageSource.getMessage("register.notification", null, locale);
        notificationService.createNotification(user, null, user.getUsername() + " " + notificationMessage);

        return token;
    }

    public String loginUser(User user, Locale locale) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        // Use localized messages for login errors
        if (existingUser == null || !passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return messageSource.getMessage("error.invalid.credentials", null, "Invalid username or password.", locale);
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());

        // Send a localized login notification
        String notificationMessage = messageSource.getMessage("login.notification", null, locale);
        notificationService.createNotification(existingUser, null, user.getUsername() + " " + notificationMessage);

        return token;
    }
}*/


import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.User;
import com.example.easybankproject.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.util.Locale;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private MessageSource messageSource;

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

    public String registerUser(User user, Locale locale) throws Exception {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new Exception();
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
