package com.tus.cipher.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.dto.accounts.EmployeeRole;
import com.tus.cipher.dto.LoginRequest;

// @ExtendWith(MockitoExtension.class)
@SpringBootTest		
class SysAdminControllerTest {

    @Autowired
	SysAdminController sysAdminController;

    @MockBean
    private AccountRepository accountRepository;

    @Captor
    private ArgumentCaptor<LoginRequest> captor;

    @Test
    void testCreateNewSystemAdminAccount() {
        Account newAccount = new Account("New System Admin", "Admin1234", EmployeeRole.SYSTEM_ADMINISTRATOR);
        Account newAccountWithID = new Account("New System Admin", "Admin1234", EmployeeRole.SYSTEM_ADMINISTRATOR);
        newAccountWithID.setId(new Long(1));
        when(accountRepository.save(any())).thenReturn(newAccountWithID);
        ResponseEntity<Account> response = sysAdminController.addAccount(newAccount);
        checkCreatedAccount(newAccountWithID, response);
    }

    @Test
    void testCreateNewCustomerServiceRepAccount() {
        Account newAccount = new Account("New Customer Service Rep", "CustRep1234", EmployeeRole.CUSTOMER_SERVICE_REP);
        Account newAccountWithID = new Account("New Customer Service Rep", "CustRep1234", EmployeeRole.CUSTOMER_SERVICE_REP);
        newAccountWithID.setId(new Long(1));
        when(accountRepository.save(any())).thenReturn(newAccountWithID);
        ResponseEntity<Account> response = sysAdminController.addAccount(newAccount);
        checkCreatedAccount(newAccountWithID, response);
    }

    @Test
    void testCreateNewSupportEngineerAccount() {
        Account newAccount = new Account("New Support Engineer", "SuppEng1234", EmployeeRole.SUPPORT_ENGINEER);
        Account newAccountWithID = new Account("New Support Engineer", "SuppEng1234", EmployeeRole.SUPPORT_ENGINEER);
        newAccountWithID.setId(new Long(1));
        when(accountRepository.save(any())).thenReturn(newAccountWithID);
        ResponseEntity<Account> response = sysAdminController.addAccount(newAccount);
        checkCreatedAccount(newAccountWithID, response);
    }

    @Test
    void testCreateNewNetworkEngineerAccount() {
        Account newAccount = new Account("New Network Engineer", "CustRep1234", EmployeeRole.NETWORK_ENGINEER);
        Account newAccountWithID = new Account("New Network Engineer", "CustRep1234", EmployeeRole.NETWORK_ENGINEER);
        newAccountWithID.setId(new Long(1));
        when(accountRepository.save(any())).thenReturn(newAccountWithID);
        ResponseEntity<Account> response = sysAdminController.addAccount(newAccount);
        checkCreatedAccount(newAccountWithID, response);
    }

    public void checkCreatedAccount(Account correctAccount, ResponseEntity<Account> response) {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Account responseBody = response.getBody();
        System.out.println(responseBody);
        assertNotNull(responseBody);
        assertEquals(correctAccount.getId(), responseBody.getId()); 
        assertEquals(correctAccount.getUsername(), responseBody.getUsername());
        assertEquals(correctAccount.getPassword(), responseBody.getPassword());
        assertEquals(correctAccount.getRole(), responseBody.getRole());
    }
}