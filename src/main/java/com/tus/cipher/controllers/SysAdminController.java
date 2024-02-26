package com.tus.cipher.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.dto.accounts.AccountFactory;
import com.tus.cipher.responses.ApiError;
import com.tus.cipher.responses.ApiResponse;

@RestController
@RequestMapping("/sysadmin")
public class SysAdminController {

	private AccountRepository accountRepository;

	public SysAdminController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@PostMapping("/accounts")
	public ApiResponse<Account> addAccount(@Valid @RequestBody Account account) {
		account = AccountFactory.createAccount(account);
		if (!securePassword(account)) {
			ApiError error = ApiError.of("Password not secure", "password length must be at least 8 characters");
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		}

		Optional<Account> accountOptional = accountRepository.findByUsername(account.getUsername());
		if (accountOptional.isPresent()) {
			// Account already exist in database already
			ApiError error = ApiError.of("Username already exist", "");
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		} else {
			// Create the new Account
			Account savedAccoount = accountRepository.save(account);
			return ApiResponse.success(HttpStatus.OK.value(), savedAccoount);
		}

	}

	boolean securePassword(Account account) {
		return account.getPassword().length() >= 8;
	}
}
