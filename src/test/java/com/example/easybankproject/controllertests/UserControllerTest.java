package com.example.easybankproject.controllertests;

import com.example.easybankproject.controllers.UserController;
import com.example.easybankproject.models.User;
import com.example.easybankproject.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setEmail("test@example.com");
        user.setAddress("123 Test St");
        user.setPhonenumber(123456789);
        user.setFirstname("Test");
        user.setLastname("User");
    }

    @Test
    public void testGetUserDataSuccess() throws Exception {
        // Arrange
        String token = "Bearer validToken";
        when(userService.getUserData("validToken")).thenReturn(user);

        // Act & Assert
        mockMvc.perform(get("/api/user/me")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.address").value("123 Test St"))
                .andExpect(jsonPath("$.phonenumber").value(123456789))
                .andExpect(jsonPath("$.firstname").value("Test"))
                .andExpect(jsonPath("$.lastname").value("User"));
    }

    @Test
    public void testUpdateUserDataSuccess() throws Exception {
        // Arrange
        String token = "Bearer validToken";
        when(userService.updateUserData("validToken", user)).thenReturn(user);

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(put("/api/user/update/me")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.address").value("123 Test St"))
                .andExpect(jsonPath("$.phonenumber").value(123456789))
                .andExpect(jsonPath("$.firstname").value("Test"))
                .andExpect(jsonPath("$.lastname").value("User"));
    }

    @Test
    public void testUpdateUserDataUserNotFound() throws Exception {
        // Arrange
        String token = "Bearer validToken";
        when(userService.updateUserData("validToken", user)).thenReturn(null);

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(put("/api/user/update/me")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        // Arrange
        when(userService.registerUser(user)).thenReturn("User registered successfully");

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    public void testRegisterUserAlreadyExists() throws Exception {
        // Arrange
        when(userService.registerUser(user)).thenReturn("Username already exists.");

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username already exists."));
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        // Arrange
        when(userService.loginUser(user)).thenReturn("ValidToken123");

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("ValidToken123"));
    }

    @Test
    public void testLoginUserInvalidPassword() throws Exception {
        // Arrange
        when(userService.loginUser(user)).thenReturn("Invalid username or password.");

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password."));
    }
}
/*package com.example.easybankproject.controllertests;


import com.example.easybankproject.controllers.UserController;
import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.NotificationRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.User;
import com.example.easybankproject.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private User user;
    private User existingUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        user.setFirstname("Test");
        user.setLastname("User");
        user.setPhonenumber(1234567890);
        user.setAddress("123 Test St");

        existingUser = new User();
        existingUser.setUsername("testuser");
        existingUser.setPassword("hashedPassword");
        existingUser.setEmail("testuser@example.com");
        existingUser.setFirstname("Test");
        existingUser.setLastname("User");
        existingUser.setPhonenumber(1234567890);
        existingUser.setAddress("123 Test St");
    }

    @Test
    public void testGetUserData() throws Exception {
        // Arrange
        String token = "Bearer validToken";
        when(jwtUtil.extractUsername("validToken")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(existingUser);

        // Act & Assert
        mockMvc.perform(get("/api/user/me")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"))
                .andExpect(jsonPath("$.firstname").value("Test"))
                .andExpect(jsonPath("$.lastname").value("User"))
                .andExpect(jsonPath("$.phonenumber").value(1234567890))
                .andExpect(jsonPath("$.address").value("123 Test St"));
    }

    @Test
    public void testUpdateUserDataSuccess() throws Exception {
        // Arrange
        String token = "Bearer validToken";
        when(jwtUtil.extractUsername("validToken")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(existingUser);

        User updatedUser = new User();
        updatedUser.setEmail("newemail@example.com");
        updatedUser.setAddress("456 New St");
        updatedUser.setPhonenumber(987654321);
        updatedUser.setFirstname("New");
        updatedUser.setLastname("Name");

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(updatedUser);

        mockMvc.perform(put("/api/user/update/me")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("newemail@example.com"))
                .andExpect(jsonPath("$.address").value("456 New St"))
                .andExpect(jsonPath("$.phonenumber").value(987654321))
                .andExpect(jsonPath("$.firstname").value("New"))
                .andExpect(jsonPath("$.lastname").value("Name"));
    }

    @Test
    public void testUpdateUserDataUserNotFound() throws Exception {
        // Arrange
        String token = "Bearer validToken";
        when(jwtUtil.extractUsername("validToken")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(null);

        User updatedUser = new User();
        updatedUser.setEmail("newemail@example.com");
        updatedUser.setAddress("456 New St");
        updatedUser.setPhonenumber(987654321);
        updatedUser.setFirstname("New");
        updatedUser.setLastname("Name");

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(updatedUser);

        mockMvc.perform(put("/api/user/update/me")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(jwtUtil.generateToken("testuser")).thenReturn("ValidToken123");

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("ValidToken123"));
    }

    @Test
    public void testRegisterUserAlreadyExists() throws Exception {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(existingUser);

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username already exists."));
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(existingUser);
        when(passwordEncoder.matches("password123", existingUser.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken("testuser")).thenReturn("ValidToken123");

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("ValidToken123"));
    }

    @Test
    public void testLoginUserInvalidPassword() throws Exception {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(existingUser);
        when(passwordEncoder.matches("wrongpassword", existingUser.getPassword())).thenReturn(false);

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password."));
    }

    @Test
    public void testLoginUserNotFound() throws Exception {
        // Arrange
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(null);

        // Act & Assert
        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password."));
    }
}
*/