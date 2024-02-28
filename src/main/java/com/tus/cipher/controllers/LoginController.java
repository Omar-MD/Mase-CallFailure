package com.tus.cipher.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.LoginRequest;
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.responses.ApiError;
import com.tus.cipher.responses.ApiResponse;

@RestController
public class LoginController {

	AccountRepository accountRepository;

	public LoginController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@PostMapping("/login")
	public ApiResponse<String> login(@RequestBody LoginRequest loginDetail) {

		String username = loginDetail.getUsername();
		String password = loginDetail.getPassword();

		Optional<Account> account = accountRepository.findByUsername(username);

		if (account.isPresent()) {
			Account loginAccount = account.get();

			if (loginAccount.getPassword().equals(password)) {
				// Success
				return ApiResponse.success(HttpStatus.OK.value(), loginAccount.getRole().name());

			} else {
				// Invalid Password
				ApiError error = ApiError.of("Invalid Credentials", "Username or Password is incorrect");
				return ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), error);
			}
		} else {
			// Invalid Username
			ApiError error = ApiError.of("Invalid Credentials", "Username or Password is incorrect");
			return ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), error);

		}
	}
}
