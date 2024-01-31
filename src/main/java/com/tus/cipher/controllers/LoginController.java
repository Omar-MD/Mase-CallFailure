package com.tus.cipher.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.Account;
import com.tus.cipher.dto.LoginRequest;
import com.tus.cipher.exceptions.ApiError;
import com.tus.cipher.exceptions.ApiResponse;

@RestController
public class LoginController {
	@Autowired
	AccountRepository accountRepository;

	@PostMapping("/login")
	public ApiResponse<String> login(@RequestBody LoginRequest loginDetail) {

		System.out.println(
				"Login endpoint accessed: " + loginDetail.getUsername() + ", pass: " + loginDetail.getPassword());

		Optional<Account> account = accountRepository.findByUsername(loginDetail.getUsername());

		if (account.isPresent()) {
			Account loginAccount = account.get();

			if (loginAccount.getPassword().equals(loginDetail.getPassword())) {
				// Success
				System.out.println("Logged in successfully");
				return ApiResponse.success(HttpStatus.OK.value(), loginAccount.getRole());

			} else {
				// Invalid Password
				System.out.println("Wrong password");
				ApiError error = ApiError.of("Invalid Credentials", "Username or Password is incorrect");
				return ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), error);
			}
		} else {
			// Invalid Username
			System.out.println("Account not found");
			ApiError error = ApiError.of("Invalid Credentials", "Username or Password is incorrect");
			return ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), error);

		}
	}
}
