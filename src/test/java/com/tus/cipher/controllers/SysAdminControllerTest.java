package com.tus.cipher.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.dto.accounts.EmployeeRole;
import com.tus.cipher.requests.CreateAccountRequest;
import com.tus.cipher.responses.ApiResponse;

class SysAdminControllerTest {

	private SysAdminController sysAdminController;
	private AccountRepository accountRepositoryMock;
	private PasswordEncoder passwordEncoderMock;

	@BeforeEach
	void setUp() {
		accountRepositoryMock = mock(AccountRepository.class);
		passwordEncoderMock = mock(PasswordEncoder.class);
		sysAdminController = new SysAdminController(accountRepositoryMock, passwordEncoderMock);
	}

	@Test
	void testCreateNewSystemAdminAccount() {
		CreateAccountRequest newUser = new CreateAccountRequest();
		newUser.setUsername("New System Admin");
		newUser.setPassword("Admin1234");
		newUser.setRole(EmployeeRole.SYSTEM_ADMINISTRATOR);

		Account newAccountWithID = new Account("New System Admin", "Admin1234", EmployeeRole.SYSTEM_ADMINISTRATOR);
		newAccountWithID.setId(Long.valueOf(1));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		ApiResponse<CreateAccountRequest> response = sysAdminController.addAccount(newUser);
		checkCreatedAccount(newAccountWithID, response);
	}

	@Test
	void testCreateNewCustomerServiceRepAccount() {
		CreateAccountRequest newUser = new CreateAccountRequest();
		newUser.setUsername("New Customer Service Rep");
		newUser.setPassword("CustRep1234");
		newUser.setRole(EmployeeRole.CUSTOMER_SERVICE_REP);

		Account newAccountWithID = new Account("New Customer Service Rep", "CustRep1234", EmployeeRole.CUSTOMER_SERVICE_REP);
		newAccountWithID.setId(Long.valueOf(2));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		ApiResponse<CreateAccountRequest> response = sysAdminController.addAccount(newUser);

		checkCreatedAccount(newAccountWithID, response);
	}

	@Test
	void testCreateNewSupportEngineerAccount() {
		CreateAccountRequest newUser = new CreateAccountRequest();
		newUser.setUsername("New Support Engineer");
		newUser.setPassword("SuppEng1234");
		newUser.setRole(EmployeeRole.SUPPORT_ENGINEER);

		Account newAccountWithID = new Account("New Support Engineer", "SuppEng1234", EmployeeRole.SUPPORT_ENGINEER);
		newAccountWithID.setId(Long.valueOf(3));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		ApiResponse<CreateAccountRequest> response = sysAdminController.addAccount(newUser);

		checkCreatedAccount(newAccountWithID, response);
	}

	@Test
	void testCreateNewNetworkEngineerAccount() {
		CreateAccountRequest newUser = new CreateAccountRequest();
		newUser.setUsername("New Network Engineer");
		newUser.setPassword("CustRep1234");
		newUser.setRole(EmployeeRole.NETWORK_ENGINEER);

		Account newAccountWithID = new Account("New Network Engineer", "CustRep1234", EmployeeRole.NETWORK_ENGINEER);
		newAccountWithID.setId(Long.valueOf(4));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		ApiResponse<CreateAccountRequest> response = sysAdminController.addAccount(newUser);

		checkCreatedAccount(newAccountWithID, response);
	}

	public void checkCreatedAccount(Account correctAccount, ApiResponse<CreateAccountRequest> response) {
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		assertNull(response.getError());
		CreateAccountRequest responseBody = response.getData();
		assertEquals(correctAccount.getUsername(), responseBody.getUsername());
		assertEquals(correctAccount.getPassword(), responseBody.getPassword());
		assertEquals(correctAccount.getRole(), responseBody.getRole());
	}

	@Test
	void testBadPassword() {
		CreateAccountRequest newUser = new CreateAccountRequest();
		newUser.setUsername("Test User");
		newUser.setPassword("short");
		newUser.setRole(EmployeeRole.NETWORK_ENGINEER);

		ApiResponse<CreateAccountRequest> response = sysAdminController.addAccount(newUser);

		assertNull(response.getData());
		assertEquals("password length must be at least 8 characters", response.getError().getDetails());
		assertEquals("Password not secure", response.getError().getErrorMsg());
	}

	@Test
	void testExistingUsername() {
		CreateAccountRequest newUser = new CreateAccountRequest();
		newUser.setUsername("Existing Username");
		newUser.setPassword("Val1d_Passw0rd!");
		newUser.setRole(EmployeeRole.SYSTEM_ADMINISTRATOR);

		Optional<Account> exisitingAcc = Optional.ofNullable(new Account("Existing Username", "Val1d_Passw0rd!", EmployeeRole.SYSTEM_ADMINISTRATOR));
		when(accountRepositoryMock.findByUsername(newUser.getUsername())).thenReturn(exisitingAcc);

		ApiResponse<CreateAccountRequest> response = sysAdminController.addAccount(newUser);

		assertNotNull(response);
		assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
		assertNull(response.getData());
		assertEquals("Username already exist!", response.getError().getDetails());
		assertEquals("Invalid Username", response.getError().getErrorMsg());
	}
}