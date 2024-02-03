package com.tus.cipher.controllers;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.ImportRequest;
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.dto.accounts.AccountFactory;
import com.tus.cipher.exceptions.ApiError;
import com.tus.cipher.exceptions.ApiResponse;
import com.tus.cipher.services.ImportService;

@RestController
@RequestMapping("/sysadmin")
public class SysAdminController {

	@Autowired
	ImportService importService;

	@Autowired
    AccountRepository accountRepository;

	@PostMapping("/import")
	public ApiResponse<String> importData(@Valid @RequestBody ImportRequest importRequest) {

		try {
			importService.importFile(importRequest.getFilename());
			return ApiResponse.success(HttpStatus.OK.value(), "Import process complete");

		} catch (IOException ioe) {
			ApiError error = ApiError.of("Import failed", ioe.getMessage());
			return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), error);
		}
	}

	@PostMapping("/accounts")
    public ApiResponse<Account> addAccount(@Valid @RequestBody Account account) {
        account = AccountFactory.createAccount(account);
		if(securePassword(account)) {
			ApiError error = ApiError.of("Password not secure", "password length must be at least 8 characters");
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		}
		Optional<Account> accountOptional = accountRepository.findByUsername(account.getUsername());
		if(accountOptional.isPresent()) {
			// Account already exist in database already
			ApiError error = ApiError.of("Username already exist", "");
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		} else {
			// Create the new Account
			Account savedAccoount = accountRepository.save(account);
        	return ApiResponse.success(HttpStatus.OK.value(), savedAccoount);
		}

    }

	private boolean securePassword(Account account) {
		return account.getPassword().length() >= 8;
	}
}
