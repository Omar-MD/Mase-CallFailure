package com.tus.cipher.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.Account;
import com.tus.cipher.dto.LoginRequest;

@SpringBootTest
// @ExtendWith(MockitoExtension.class)					//	using this annotation, avoids running the spring boot container i.e. faster
class MaseProjectApplicationControllerTests {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private AccountRepository accountRepository;

    @Captor
    private ArgumentCaptor<LoginRequest> captor;  

    @Test
    void loginTestSuccess() {
        // Mock login credentials and accountRepository behavior
        LoginRequest loginRequest = new LoginRequest("testUsername", "testPassword");
        Account testAccount = new Account(1L, "testUsername", "testPassword", "testRole");
        when(accountRepository.findByUsername("testUsername")).thenReturn(Optional.of(testAccount));
        // Call the SystemAdminHomepage method, verify http response status including body
        ResponseEntity<Account> response = loginService.SystemAdminHomepage(loginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Account responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(testAccount.getId(), responseBody.getId());
        assertEquals(testAccount.getUsername(), responseBody.getUsername());
        assertEquals(testAccount.getPassword(), responseBody.getPassword());
        assertEquals(testAccount.getRole(), responseBody.getRole());
    }

    @Test
    void loginTestIncorrectPassword() {
        // Mock invalid password and accountRepository behavior
        LoginRequest loginRequest = new LoginRequest("testUsername", "incorrectPassword");
        Account testAccount = new Account(1L, "testUsername", "testPassword", "testRole");
        when(accountRepository.findByUsername("testUsername")).thenReturn(Optional.of(testAccount));
        // Call the SystemAdminHomepage method, verify http response status
        ResponseEntity<Account> response = loginService.SystemAdminHomepage(loginRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void loginTestInvalidUsername() {
        // Mock invalid username and accountRepository behavior
        LoginRequest loginRequest = new LoginRequest("nonExistentUser", "password");
        when(accountRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());
        // Call the SystemAdminHomepage method, verify http response status
        ResponseEntity<Account> response = loginService.SystemAdminHomepage(loginRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}