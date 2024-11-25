package com.example.easybankproject.utiltests;

import com.example.easybankproject.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String token;
    private String username = "testuser";

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
        token = jwtUtil.generateToken(username);
    }

    @Test
    void testGenerateToken() {
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testValidateToken_Success() throws Exception {
        assertTrue(jwtUtil.validateToken(token, username));
    }

    @Test
    void testValidateToken_TokenNull() {
        Exception exception = assertThrows(Exception.class, () -> {
            jwtUtil.validateToken(null, username);
        });
        assertNotNull(exception);
    }

    @Test
    void testValidateToken_InvalidUsername() throws Exception {
        assertFalse(jwtUtil.validateToken(token, "invaliduser"));
    }

    @Test
    void testExtractUsername() {
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testIsTokenExpired() {
        assertFalse(jwtUtil.isTokenExpired(token));
    }

    @Test
    void testExtractAllClaims() {
        Claims claims = jwtUtil.extractAllClaims(token);
        assertNotNull(claims);
        assertEquals(username, claims.getSubject());
    }
}