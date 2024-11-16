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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
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
        user.setUser_id(1);
        user.setUsername("test1user");
        user.setEmail("test@example.com");
        user.setAddress("123 Test St");
        user.setPhonenumber(123456789);
        user.setFirstname("Test");
        user.setLastname("User");
    }

    @Test
    public void testGetUserDataSuccess() throws Exception {

        String token = "Bearer validToken";
        when(userService.getUserData("validToken")).thenReturn(user);

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

        String token = "Bearer validToken";
        when(userService.updateUserData(anyString(), any(User.class))).thenReturn(user);

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
        String token = "Bearer validToken";
        when(userService.updateUserData("validToken", user)).thenReturn(null);

        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(put("/api/user/update/me")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        when(userService.registerUser(any(User.class), any(Locale.class))).thenReturn("User registered successfully");

        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/register")
                        .header("Accept-Language", "en")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    public void testRegisterUserFailure() throws Exception {
        when(userService.registerUser(any(User.class), any(Locale.class))).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/register")
                        .header("Accept-Language", "en")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        user.setUsername("testuser");
        user.setPassword("password123");
        when(userService.loginUser(any(User.class), any(Locale.class))).thenReturn("ValidToken123");

        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/login")
                        .header("Accept-Language", "en") // Set the Accept-Language header
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("ValidToken123"));
    }

    @Test
    public void testLoginUserInvalidPassword() throws Exception {
        when(userService.loginUser(any(User.class), any(Locale.class))).thenReturn("Invalid username or password.");

        String jsonRequest = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/user/login")
                        .header("Accept-Language", "en") // Set the Accept-Language header
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password."));
    }
}