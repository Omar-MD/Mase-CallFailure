package com.tus.cipher.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.dto.accounts.EmployeeRole;

@ExtendWith(MockitoExtension.class)
class SysAdminControllerTest {

    @InjectMocks
    private SysAdminController sysAdminController;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void testCreateNewAccounts() {
        testCreateNewSystemAdminAccount();
        testCreateNewCustomerServiceRepAccount();
        testCreateNewSupportEngineerAccount();
        testCreateNewNetworkEngineerAccount();
    }

    public void testCreateNewSystemAdminAccount() {
        Account newAccount = new Account("New System Admin", "Admin1234", EmployeeRole.SYSTEM_ADMINISTRATOR);
        Account newAccountWithID = new Account("New System Admin", "Admin1234", EmployeeRole.SYSTEM_ADMINISTRATOR);
        newAccountWithID.setId(Long.valueOf(1));
        when(accountRepository.save(any())).thenReturn(newAccountWithID);
        ResponseEntity<Account> response = sysAdminController.addAccount(newAccount);
        checkCreatedAccount(newAccountWithID, response);
    }

    public void testCreateNewCustomerServiceRepAccount() {
        Account newAccount = new Account("New Customer Service Rep", "CustRep1234", EmployeeRole.CUSTOMER_SERVICE_REP);
        Account newAccountWithID = new Account("New Customer Service Rep", "CustRep1234", EmployeeRole.CUSTOMER_SERVICE_REP);
        newAccountWithID.setId(Long.valueOf(2));
        when(accountRepository.save(any())).thenReturn(newAccountWithID);
        ResponseEntity<Account> response = sysAdminController.addAccount(newAccount);
        checkCreatedAccount(newAccountWithID, response);
    }

    public void testCreateNewSupportEngineerAccount() {
        Account newAccount = new Account("New Support Engineer", "SuppEng1234", EmployeeRole.SUPPORT_ENGINEER);
        Account newAccountWithID = new Account("New Support Engineer", "SuppEng1234", EmployeeRole.SUPPORT_ENGINEER);
        newAccountWithID.setId(Long.valueOf(3));
        when(accountRepository.save(any())).thenReturn(newAccountWithID);
        ResponseEntity<Account> response = sysAdminController.addAccount(newAccount);
        checkCreatedAccount(newAccountWithID, response);
    }

    public void testCreateNewNetworkEngineerAccount() {
        Account newAccount = new Account("New Network Engineer", "CustRep1234", EmployeeRole.NETWORK_ENGINEER);
        Account newAccountWithID = new Account("New Network Engineer", "CustRep1234", EmployeeRole.NETWORK_ENGINEER);
        newAccountWithID.setId(Long.valueOf(4));
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