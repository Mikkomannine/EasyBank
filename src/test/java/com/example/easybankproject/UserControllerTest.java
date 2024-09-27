package com.example.easybankproject;

import com.example.easybankproject.db.BankAccountRepository;
import com.example.easybankproject.db.NotificationRepository;
import com.example.easybankproject.db.UserRepository;
import com.example.easybankproject.models.User;
import com.example.easybankproject.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BankAccountRepository bankAccountRepository;

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtUtil jwtUtil;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("testuser@example.com");
        user.setFirstname("Test");
        user.setLastname("User");
        user.setAddress("123 Test St");
        user.setPhonenumber(1234567890);
    }

    // Test-specific security configuration
    @Configuration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests().anyRequest().permitAll(); // Allow all requests without authentication for testing
            return http.build();
        }
    }

    @MockBean
    private CsrfTokenRepository csrfTokenRepository;

    @Test
    @WithMockUser // Mock a user for the security context
    public void testRegisterUserSuccess() throws Exception {
        // Mock repository behavior: user does not exist yet
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(null);

        // Mock password encoding behavior
        Mockito.when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // Mock JWT token generation
        Mockito.when(jwtUtil.generateToken("testuser")).thenReturn("testToken");

        // Perform POST request to /register with CSRF token
        mockMvc.perform(post("/api/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"username\": \"testuser\",\n" +
                                "    \"password\": \"password123\",\n" +
                                "    \"email\": \"testuser@example.com\",\n" +
                                "    \"firstname\": \"Test\",\n" +
                                "    \"lastname\": \"User\",\n" +
                                "    \"phonenumber\": \"1234567890\",\n" +
                                "    \"address\": \"123 Test St\"\n" +
                                "}")
                        .with(csrf()) // Include a valid CSRF token
                )
                .andExpect(status().isOk()) // Expect HTTP 200 OK status
                .andExpect(content().string("testToken")); // Expect token in response
    }
}



