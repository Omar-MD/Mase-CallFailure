package com.tus.cipher.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.dto.accounts.AccountFactory;
import com.tus.cipher.requests.CreateAccountRequest;
import com.tus.cipher.responses.ApiError;
import com.tus.cipher.responses.ApiResponse;

@RestController
@RequestMapping("/sysadmin")
public class SysAdminController {

	private AccountRepository accountRepo;
	private PasswordEncoder encoder;

	public SysAdminController(AccountRepository accountRepo, PasswordEncoder encoder) {
		this.accountRepo = accountRepo;
		this.encoder = encoder;
	}

	@PreAuthorize("hasAuthority('SYSTEM_ADMINISTRATOR')")
	@PostMapping("/accounts")
	public ApiResponse<CreateAccountRequest> addAccount(@Valid @RequestBody CreateAccountRequest accInfo) {

		if (!isPasswordSecure(accInfo.getPassword())) {
			ApiError error = ApiError.of("Password not secure", "password length must be at least 8 characters");
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		}

		Optional<Account> accountOptional = accountRepo.findByUsername(accInfo.getUsername());
		if (accountOptional.isPresent()) {
			ApiError error = ApiError.of("Invalid Username", "Username already exist!");
			return ApiResponse.error(HttpStatus.CONFLICT.value(), error);

		} else {
			Account account = AccountFactory.createAccount(
					accInfo.getUsername(),
					encoder.encode(accInfo.getPassword()),
					accInfo.getRole());
			accountRepo.save(account);
			return ApiResponse.success(HttpStatus.CREATED.value(), accInfo);
		}
	}

	public boolean isPasswordSecure(String pass) {
		return pass.length() >= 8;
	}
}
