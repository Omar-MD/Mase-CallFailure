package com.tus.cipher.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import com.tus.cipher.jwt.AccountService;
import com.tus.cipher.jwt.JwtService;
import com.tus.cipher.requests.AuthRequest;
import com.tus.cipher.responses.ApiResponse;
import com.tus.cipher.responses.AuthResponse;


class AuthControllerTest {

    private AuthenticationManager authManagerMock;
    private JwtService jwtServiceMock;
    private AccountService accountServiceMock;
    private AuthController authController;

	@BeforeEach
	void setUp() {
		authManagerMock = mock(AuthenticationManager.class);
		jwtServiceMock = mock(JwtService.class);
		accountServiceMock = mock(AccountService.class);
		authController = new AuthController(authManagerMock, jwtServiceMock, accountServiceMock);
	}

    @Test
    void testAuthenticateAndGetToken_Success() {
        // Mock AuthRequest
        AuthRequest authRequest = new AuthRequest("username", "password");

        // Mock Authentication
        Authentication authentication = mock(Authentication.class);

        when(authManagerMock.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(jwtServiceMock.generateToken("username")).thenReturn("token");
        when(accountServiceMock.loadUserRole("username")).thenReturn("ROLE_USER");
        when(authentication.isAuthenticated()).thenReturn(true);

        ApiResponse<AuthResponse> response = authController.auth(authRequest);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("token", response.getData().getToken());
        assertEquals("ROLE_USER", response.getData().getRole());

        verify(authManagerMock, times(1)).authenticate(any());
        verify(jwtServiceMock, times(1)).generateToken("username");
        verify(accountServiceMock, times(1)).loadUserRole("username");
    }

    @Test
    void testAuthenticateAndGetToken_Failure() {

        AuthRequest authRequest = new AuthRequest("username", "password");
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authManagerMock.authenticate(any())).thenReturn(authentication);

        ApiResponse<AuthResponse> response = authController.auth(authRequest);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
        // Verify interactions
        verify(authManagerMock, times(1)).authenticate(any());
        verify(jwtServiceMock, never()).generateToken(any());
        verify(accountServiceMock, never()).loadUserRole(any());
    }
}