package com.tus.cipher.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.Account;
import com.tus.cipher.dto.LoginRequest;
import com.tus.cipher.exceptions.ApiError;
import com.tus.cipher.exceptions.ApiResponse;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginService;

    @Mock
    private AccountRepository accountRepository;

    @Captor
    private ArgumentCaptor<LoginRequest> captor;

    @Test
    void loginTestSuccess() {

        LoginRequest loginRequest = new LoginRequest("testUsername", "testPassword");
        Account testAccount = new Account("testUsername", "testPassword", "testRole");

        when(accountRepository.findByUsername("testUsername")).thenReturn(Optional.of(testAccount));

        ApiResponse<String> response = loginService.login(loginRequest);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(testAccount.getRole(), response.getData());
        assertNull(response.getError());
    }

    @Test
    void loginTestIncorrectPassword() {

        LoginRequest loginRequest = new LoginRequest("testUsername", "incorrectPassword");
        Account testAccount = new Account("testUsername", "testPassword", "testRole");
        ApiError error = ApiError.of("Invalid Credentials", "Username or Password is incorrect");

        when(accountRepository.findByUsername("testUsername")).thenReturn(Optional.of(testAccount));

        ApiResponse<String> response = loginService.login(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
        assertEquals(error.getErrorMsg(), response.getError().getErrorMsg());
        assertEquals(error.getDetails(), response.getError().getDetails());
        assertNull(response.getData());
    }

    @Test
    void loginTestInvalidUsername() {

        LoginRequest loginRequest = new LoginRequest("nonExistentUser", "password");
        ApiError error = ApiError.of("Invalid Credentials", "Username or Password is incorrect");

        when(accountRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        ApiResponse<String> response = loginService.login(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
        assertEquals(error.getErrorMsg(), response.getError().getErrorMsg());
        assertEquals(error.getDetails(), response.getError().getDetails());
        assertNull(response.getData());
    }
}