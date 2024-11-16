package com.example.easybankproject.controllers;



import com.example.easybankproject.models.User;
import com.example.easybankproject.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.util.Locale;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringHttpMessageConverter stringHttpMessageConverter;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/me")
    public ResponseEntity<User> getUserData(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        User user = userService.getUserData(jwtToken);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/me")
    public ResponseEntity<User> updateUserData(@RequestHeader("Authorization") String token, @RequestBody User updatedUser) {
        String jwtToken = token.substring(7);
        User user = userService.updateUserData(jwtToken, updatedUser);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user, @RequestHeader("Accept-Language") Locale locale) {
        try {
            String result = userService.registerUser(user, locale);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user, @RequestHeader("Accept-Language") Locale locale) {
        String result = userService.loginUser(user, locale);
        if (result == null || result.equals("Invalid username or password.")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Invalid username or password.");
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(result);
    }

}


