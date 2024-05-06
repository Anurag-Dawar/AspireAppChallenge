package com.example.aspireloanapi;

import com.example.aspireloanapi.security.JwtHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtHelperTest {

    @InjectMocks
    private JwtHelper jwtHelper;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        userDetails = User.builder()
                .username("testUser")
                .password("testPassword")
                .roles("USER")
                .build();
    }

    @Test
    void getUsernameFromToken_ShouldReturnUsername_WhenValidTokenProvided() {
        // Given
        String token = jwtHelper.generateToken(userDetails);

        // When
        String extractedUsername = jwtHelper.getUsernameFromToken(token);

        // Then
        assertEquals(userDetails.getUsername(), extractedUsername);
    }

    @Test
    void getExpirationDateFromToken_ShouldReturnExpirationDate_WhenValidTokenProvided() {
        // Given
        String token = jwtHelper.generateToken(userDetails);

        // When
        long expirationMillis = jwtHelper.getExpirationDateFromToken(token).getTime();

        // Then
        assertTrue(expirationMillis > System.currentTimeMillis());
    }

    @Test
    void validateToken_ShouldReturnTrue_WhenValidTokenProvided() {
        // Given
        String token = jwtHelper.generateToken(userDetails);

        // When
        boolean isValid = jwtHelper.validateToken(token, userDetails);

        // Then
        assertTrue(isValid);
    }

    @Test
    void validateToken_ShouldReturnFalse_WhenInvalidTokenProvided() {
        // Given
        String token = "invalid-token";

        // When
        boolean isValid = jwtHelper.validateToken(token, userDetails);


        // Then
        assertFalse(isValid);
    }
}

