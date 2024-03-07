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
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.dto.accounts.EmployeeRole;
import com.tus.cipher.requests.LoginRequest;
import com.tus.cipher.responses.ApiError;
import com.tus.cipher.responses.ApiResponse;

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

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUsername");
        loginRequest.setPassword("testPassword");

        Account testAccount = new Account("testUsername", "testPassword", EmployeeRole.SYSTEM_ADMINISTRATOR);
        when(accountRepository.findByUsername("testUsername")).thenReturn(Optional.of(testAccount));

        ApiResponse<String> response = loginService.login(loginRequest);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(testAccount.getRole().name(), response.getData());
        assertNull(response.getError());
    }

    @Test
    void loginTestIncorrectPassword() {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUsername");
        loginRequest.setPassword("incorrectPassword");

        Account testAccount = new Account("testUsername", "testPassword", EmployeeRole.SYSTEM_ADMINISTRATOR);
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