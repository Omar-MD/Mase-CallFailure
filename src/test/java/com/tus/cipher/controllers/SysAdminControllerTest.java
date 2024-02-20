package com.tus.cipher.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.ImportRequest;
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.dto.accounts.EmployeeRole;
import com.tus.cipher.responses.ApiResponse;
import com.tus.cipher.services.DataValidator;
import com.tus.cipher.services.ImportParams;
import com.tus.cipher.services.ImportService;
import com.tus.cipher.services.sheets.BaseDataSheet;
import com.tus.cipher.services.sheets.BaseSheetProcessor;

class SysAdminControllerTest {

	private SysAdminController sysAdminController;
	private AccountRepository accountRepositoryMock;
	private ImportService importServiceMock;
	private ImportParams importParamsMock;
	private DataValidator dataValidatorMock;

	@BeforeEach
	void setUp() {
		// Initialise mocks
		accountRepositoryMock = mock(AccountRepository.class);
		importParamsMock = mock(ImportParams.class);
		importServiceMock = mock(ImportService.class);
		dataValidatorMock = mock(DataValidator.class);

		// Configure behaviours
		when(importParamsMock.getRefProcessors()).thenReturn(List.of(mock(BaseSheetProcessor.class)));
		when(importParamsMock.getDataValidator()).thenReturn(dataValidatorMock);
		when(importParamsMock.getBaseDataSheet()).thenReturn(mock(BaseDataSheet.class));

		doNothing().when(dataValidatorMock).prepareValidator();
	}

	@Test
	void testImportSuccess() throws IOException {
		// Mock import request
		ImportRequest importRequest = new ImportRequest();
		importRequest.setFilename("TestFile.xls");

		// Create a Spy
        sysAdminController = spy(new SysAdminController());
        sysAdminController.setAccountRepository(accountRepositoryMock);
        sysAdminController.setImportService(importServiceMock);

	    // Mock  ImportService & createWorkbook method
	    doReturn(new HSSFWorkbook()).when(sysAdminController).createWorkbook(anyString());
	    doNothing().when(importServiceMock).importWorkBook(any(HSSFWorkbook.class));

		ApiResponse<String> responseEntity = sysAdminController.importData(importRequest);

		// Verify response
		assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode());
		assertEquals("Success", responseEntity.getStatus());
		assertEquals("<strong>Import Summary</strong><br/>Total Count of Errors: 0<br/>Import successful. No errors found.", responseEntity.getData());
	}

	@Test
	void testImportFailure() throws IOException {
		ImportRequest importRequest = new ImportRequest();
		sysAdminController = new SysAdminController(importServiceMock, accountRepositoryMock);
		ApiResponse<String> responseEntity = sysAdminController.importData(importRequest);

		// Verify response
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCode());
		assertEquals("Error", responseEntity.getStatus());
		assertEquals("Import failed", responseEntity.getError().getErrorMsg());
		assertEquals("src/main/resources/null", responseEntity.getError().getDetails());
	}

	@Test
	void testCreateNewSystemAdminAccount() {
		Account newAccount = new Account();
		newAccount.setUsername("New System Admin");
		newAccount.setPassword("Admin1234");
		newAccount.setRole(EmployeeRole.SYSTEM_ADMINISTRATOR);

		Account newAccountWithID = new Account("New System Admin", "Admin1234", EmployeeRole.SYSTEM_ADMINISTRATOR);
		newAccountWithID.setId(Long.valueOf(1));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		sysAdminController = new SysAdminController(importServiceMock, accountRepositoryMock);
		ApiResponse<Account> response = sysAdminController.addAccount(newAccount);

		checkCreatedAccount(newAccountWithID, response);
	}

	@Test
	void testCreateNewCustomerServiceRepAccount() {
		Account newAccount = new Account("New Customer Service Rep", "CustRep1234", EmployeeRole.CUSTOMER_SERVICE_REP);
		Account newAccountWithID = new Account("New Customer Service Rep", "CustRep1234",
				EmployeeRole.CUSTOMER_SERVICE_REP);
		newAccountWithID.setId(Long.valueOf(2));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		sysAdminController = new SysAdminController(importServiceMock, accountRepositoryMock);
		ApiResponse<Account> response = sysAdminController.addAccount(newAccount);

		checkCreatedAccount(newAccountWithID, response);
	}

	@Test
	void testCreateNewSupportEngineerAccount() {
		Account newAccount = new Account("New Support Engineer", "SuppEng1234", EmployeeRole.SUPPORT_ENGINEER);
		Account newAccountWithID = new Account("New Support Engineer", "SuppEng1234", EmployeeRole.SUPPORT_ENGINEER);
		newAccountWithID.setId(Long.valueOf(3));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		sysAdminController = new SysAdminController(importServiceMock, accountRepositoryMock);
		ApiResponse<Account> response = sysAdminController.addAccount(newAccount);

		checkCreatedAccount(newAccountWithID, response);
	}

	@Test
	void testCreateNewNetworkEngineerAccount() {
		Account newAccount = new Account("New Network Engineer", "CustRep1234", EmployeeRole.NETWORK_ENGINEER);
		Account newAccountWithID = new Account("New Network Engineer", "CustRep1234", EmployeeRole.NETWORK_ENGINEER);
		newAccountWithID.setId(Long.valueOf(4));
		when(accountRepositoryMock.save(any())).thenReturn(newAccountWithID);

		sysAdminController = new SysAdminController(importServiceMock, accountRepositoryMock);
		ApiResponse<Account> response = sysAdminController.addAccount(newAccount);

		checkCreatedAccount(newAccountWithID, response);
	}
	public void checkCreatedAccount(Account correctAccount, ApiResponse<Account> response) {
		assertNotNull(response);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		// assertEquals(HttpStatus.OK, response.getStatusCode());
		// Account responseBody = response.getBody();
		assertNull(response.getError());
		Account responseBody = response.getData();
		assertEquals(correctAccount.getId(), responseBody.getId());
		assertEquals(correctAccount.getUsername(), responseBody.getUsername());
		assertEquals(correctAccount.getPassword(), responseBody.getPassword());
		assertEquals(correctAccount.getRole(), responseBody.getRole());
	}

	@Test
	void testBadPassword() {
		Account newAccount = new Account("Test User", "short", EmployeeRole.NETWORK_ENGINEER);

		sysAdminController = new SysAdminController(importServiceMock, accountRepositoryMock);
		ApiResponse<Account> response = sysAdminController.addAccount(newAccount);

		assertNull(response.getData());
		assertEquals("password length must be at least 8 characters", response.getError().getDetails());
		assertEquals("Password not secure", response.getError().getErrorMsg());
	}

	@Test
	void testExistingUsername() {
		Account newAccount = new Account("Existing Username", "Val1d_Passw0rd!", EmployeeRole.SYSTEM_ADMINISTRATOR);

		when(accountRepositoryMock.findByUsername(newAccount.getUsername())).thenReturn(Optional.of(newAccount));

		sysAdminController = new SysAdminController(importServiceMock, accountRepositoryMock);
		ApiResponse<Account> response = sysAdminController.addAccount(newAccount);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		assertNull(response.getData());
		assertEquals("", response.getError().getDetails());
		assertEquals("Username already exist", response.getError().getErrorMsg());
	}

}