package com.example.easybankproject.controllertests;


import com.example.easybankproject.controllers.NotificationController;
import com.example.easybankproject.db.NotificationRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.Notification;
import com.example.easybankproject.models.User;
import com.example.easybankproject.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private NotificationController notificationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private User user;
    private List<Notification> notifications;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();

        user = new User();
        user.setUsername("testuser");

        Notification notification1 = new Notification();
        notification1.setNotificationId(1);
        notification1.setContent("Notification 1");

        Notification notification2 = new Notification();
        notification2.setNotificationId(2);
        notification2.setContent("Notification 2");

        notifications = new ArrayList<>();
        notifications.add(notification1);
        notifications.add(notification2);
    }

    @Test
    public void testGetUserNotifications() throws Exception {
        // Arrange
        String token = "Bearer validToken";
        when(jwtUtil.extractUsername("validToken")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(notificationRepository.findByUser(user)).thenReturn(notifications);

        // Act & Assert
        mockMvc.perform(get("/api/notifications")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Notification 1"))
                .andExpect(jsonPath("$[1].content").value("Notification 2"));
    }

    @Test
    public void testGetNotificationsCount() throws Exception {
        // Arrange
        String token = "Bearer validToken";
        when(jwtUtil.extractUsername("validToken")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(notificationRepository.findByUser(user)).thenReturn(notifications);

        // Act & Assert
        mockMvc.perform(get("/api/notifications/count")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    public void testDeleteNotificationSuccess() throws Exception {
        // Arrange
        int notificationId = 1;
        when(notificationRepository.existsById(notificationId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/notifications/{id}", notificationId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteNotificationNotFound() throws Exception {
        // Arrange
        int notificationId = 1;
        when(notificationRepository.existsById(notificationId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/notifications/{id}", notificationId))
                .andExpect(status().isNotFound());
    }
}
