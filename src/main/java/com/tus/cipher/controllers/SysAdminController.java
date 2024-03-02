package com.tus.cipher.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.CreateUserRequest;
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
	public ApiResponse<CreateUserRequest> addAccount(@Valid @RequestBody CreateUserRequest newUser) {

		if (!securePassword(newUser.getPassword())) {
			ApiError error = ApiError.of("Password not secure", "password length must be at least 8 characters");
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		}

		Optional<Account> accountOptional = accountRepository.findByUsername(newUser.getUsername());
		if (accountOptional.isPresent()) {
			ApiError error = ApiError.of("Username already exist", "");
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);

		} else {
			// Create the new Account
			Account account = AccountFactory.createAccount(newUser.getUsername(), newUser.getPassword(), newUser.getRole());
			accountRepository.save(account);
			return ApiResponse.success(HttpStatus.OK.value(), newUser);
		}

	}

	boolean securePassword(String password) {
		return password.length() >= 8;
	}
}
