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

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.CreateUserRequest;
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.dto.accounts.EmployeeRole;
import com.tus.cipher.responses.ApiResponse;

class SysAdminControllerTest {

	private SysAdminController sysAdminController;
	private AccountRepository accountRepositoryMock;


	@BeforeEach
	void setUp() {
		// Initialise mocks
		accountRepositoryMock = mock(AccountRepository.class);
	}

	@Test
	void testCreateNewSystemAdminAccount() {
		CreateUserRequest newUser = new CreateUserRequest();
		newUser.setUsername("New System Admin");
		newUser.setPassword("Admin1234");
		newUser.setRole(EmployeeRole.SYSTEM_ADMINISTRATOR);

		Account newAccountWithID = new Account("New System Admin", "Admin1234", EmployeeRole.SYSTEM_ADMINISTRATOR);
		newAccountWithID.setId(Long.valueOf(1));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		sysAdminController = new SysAdminController(accountRepositoryMock);
		ApiResponse<CreateUserRequest> response = sysAdminController.addAccount(newUser);

		checkCreatedAccount(newAccountWithID, response);
	}

	@Test
	void testCreateNewCustomerServiceRepAccount() {
		CreateUserRequest newUser = new CreateUserRequest();
		newUser.setUsername("New Customer Service Rep");
		newUser.setPassword("CustRep1234");
		newUser.setRole(EmployeeRole.CUSTOMER_SERVICE_REP);

		Account newAccountWithID = new Account("New Customer Service Rep", "CustRep1234", EmployeeRole.CUSTOMER_SERVICE_REP);
		newAccountWithID.setId(Long.valueOf(2));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		sysAdminController = new SysAdminController(accountRepositoryMock);
		ApiResponse<CreateUserRequest> response = sysAdminController.addAccount(newUser);

		checkCreatedAccount(newAccountWithID, response);
	}

	@Test
	void testCreateNewSupportEngineerAccount() {
		CreateUserRequest newUser = new CreateUserRequest();
		newUser.setUsername("New Support Engineer");
		newUser.setPassword("SuppEng1234");
		newUser.setRole(EmployeeRole.SUPPORT_ENGINEER);

		Account newAccountWithID = new Account("New Support Engineer", "SuppEng1234", EmployeeRole.SUPPORT_ENGINEER);
		newAccountWithID.setId(Long.valueOf(3));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		sysAdminController = new SysAdminController(accountRepositoryMock);
		ApiResponse<CreateUserRequest> response = sysAdminController.addAccount(newUser);

		checkCreatedAccount(newAccountWithID, response);
	}

	@Test
	void testCreateNewNetworkEngineerAccount() {
		CreateUserRequest newUser = new CreateUserRequest();
		newUser.setUsername("New Network Engineer");
		newUser.setPassword("CustRep1234");
		newUser.setRole(EmployeeRole.NETWORK_ENGINEER);

		Account newAccountWithID = new Account("New Network Engineer", "CustRep1234", EmployeeRole.NETWORK_ENGINEER);
		newAccountWithID.setId(Long.valueOf(4));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		sysAdminController = new SysAdminController(accountRepositoryMock);
		ApiResponse<CreateUserRequest> response = sysAdminController.addAccount(newUser);

		checkCreatedAccount(newAccountWithID, response);
	}

	public void checkCreatedAccount(Account correctAccount, ApiResponse<CreateUserRequest> response) {
		assertNotNull(response);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertNull(response.getError());
		CreateUserRequest responseBody = response.getData();
		assertEquals(correctAccount.getUsername(), responseBody.getUsername());
		assertEquals(correctAccount.getPassword(), responseBody.getPassword());
		assertEquals(correctAccount.getRole(), responseBody.getRole());
	}

	@Test
	void testBadPassword() {
		CreateUserRequest newUser = new CreateUserRequest();
		newUser.setUsername("Test User");
		newUser.setPassword("short");
		newUser.setRole(EmployeeRole.NETWORK_ENGINEER);

		sysAdminController = new SysAdminController(accountRepositoryMock);
		ApiResponse<CreateUserRequest> response = sysAdminController.addAccount(newUser);

		assertNull(response.getData());
		assertEquals("password length must be at least 8 characters", response.getError().getDetails());
		assertEquals("Password not secure", response.getError().getErrorMsg());
	}

	@Test
	void testExistingUsername() {
		CreateUserRequest newUser = new CreateUserRequest();
		newUser.setUsername("Existing Username");
		newUser.setPassword("Val1d_Passw0rd!");
		newUser.setRole(EmployeeRole.SYSTEM_ADMINISTRATOR);

		Optional<Account> exisitingAcc = Optional.ofNullable(new Account("Existing Username", "Val1d_Passw0rd!", EmployeeRole.SYSTEM_ADMINISTRATOR));
		when(accountRepositoryMock.findByUsername(newUser.getUsername())).thenReturn(exisitingAcc);

		sysAdminController = new SysAdminController(accountRepositoryMock);
		ApiResponse<CreateUserRequest> response = sysAdminController.addAccount(newUser);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		assertNull(response.getData());
		assertEquals("", response.getError().getDetails());
		assertEquals("Username already exist", response.getError().getErrorMsg());
	}
}