package com.example.easybankproject.servicetests;

import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.User;
import com.example.easybankproject.services.BankAccountService;
import com.example.easybankproject.services.NotificationService;
import com.example.easybankproject.services.UserService;
import com.example.easybankproject.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private NotificationService notificationService;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private UserService userService;

    private User user;
    private Locale locale;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setEmail("test@example.com");
        user.setAddress("123 Test St");
        user.setPhonenumber(123456789);
        user.setFirstname("Test");
        user.setLastname("User");

        locale = Locale.ENGLISH;
    }

    @Test
    void testGetUserData_Success() {
        String token = "test-token";
        when(jwtUtil.extractUsername(token)).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User result = userService.getUserData(token);

        assertEquals(user, result);
        verify(jwtUtil).extractUsername(token);
        verify(userRepository).findByUsername("testUser");
    }

    @Test
    void testUpdateUserData_Success() {
        String token = "test-token";
        User updatedUser = new User();
        updatedUser.setEmail("updated@example.com");
        updatedUser.setAddress("456 Updated St");
        updatedUser.setPhonenumber(987654321);
        updatedUser.setFirstname("UpdatedFirstName");
        updatedUser.setLastname("UpdatedLastName");

        when(jwtUtil.extractUsername(token)).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUserData(token, updatedUser);

        assertEquals("updated@example.com", result.getEmail());
        assertEquals("456 Updated St", result.getAddress());
        assertEquals(987654321, result.getPhonenumber());
        assertEquals("UpdatedFirstName", result.getFirstname());
        assertEquals("UpdatedLastName", result.getLastname());
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUserData_UserNotFound() {
        String token = "test-token";
        User updatedUser = new User();
        when(jwtUtil.extractUsername(token)).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(null);

        User result = userService.updateUserData(token, updatedUser);

        assertNull(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterUser_Success() {
        when(userRepository.findByUsername("testUser")).thenReturn(null);
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
        when(jwtUtil.generateToken("testUser")).thenReturn("generated-jwt-token");
        when(messageSource.getMessage("register.notification", null, locale)).thenReturn("has successfully registered.");

        String token = userService.registerUser(user, locale);

        assertEquals("generated-jwt-token", token);
        verify(userRepository).save(user);
        verify(passwordEncoder).encode("testPassword");
        verify(bankAccountService).createBankAccount(user);
        verify(notificationService).createNotification(user, null, "testUser has successfully registered.");
    }

    @Test
    void testRegisterUser_UsernameExists() {
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(messageSource.getMessage("error.username.exists", null, "Username already exists.", locale))
                .thenReturn("Username already exists.");

        String result = userService.registerUser(user, locale);

        assertEquals("Username already exists.", result);
        verify(userRepository, never()).save(any(User.class));
        verify(bankAccountService, never()).createBankAccount(any(User.class));
        verify(notificationService, never()).createNotification(any(User.class), any(), anyString());
    }

    @Test
    void testLoginUser_Success() {
        User existingUser = new User();
        existingUser.setUsername("testUser");
        existingUser.setPassword("encodedPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(existingUser);
        when(passwordEncoder.matches("testPassword", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("testUser")).thenReturn("generated-jwt-token");
        when(messageSource.getMessage("login.notification", null, locale)).thenReturn("has logged in.");

        String token = userService.loginUser(user, locale);

        assertEquals("generated-jwt-token", token);
        verify(notificationService).createNotification(existingUser, null, "testUser has logged in.");
    }

    @Test
    void testLoginUser_InvalidCredentials() {
        when(userRepository.findByUsername("testUser")).thenReturn(null);
        when(messageSource.getMessage("error.invalid.credentials", null, "Invalid username or password.", locale))
                .thenReturn("Invalid username or password.");

        String result = userService.loginUser(user, locale);

        assertEquals("Invalid username or password.", result);
        verify(jwtUtil, never()).generateToken(anyString());
        verify(notificationService, never()).createNotification(any(User.class), any(), anyString());
    }
}
