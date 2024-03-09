package com.tus.cipher.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.jwt.AccountService;
import com.tus.cipher.jwt.JwtService;
import com.tus.cipher.requests.AuthRequest;
import com.tus.cipher.responses.ApiError;
import com.tus.cipher.responses.ApiResponse;
import com.tus.cipher.responses.AuthResponse;

@RestController
public class AuthController {

	private static final String INVALID_CREDENTIALS = "Invalid Credentials";
	private static final String INCORRECT_USERNAME_PASSWORD = "Username or Password is incorrect";

	private AuthenticationManager authenticationManager;
	private AccountService accountService;
	private JwtService jwtService;

	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, AccountService accountService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.accountService = accountService;
	}

	@PostMapping("/authenticate")
	public ApiResponse<AuthResponse> auth(@RequestBody AuthRequest authRequest) {

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			if (authentication.isAuthenticated()) {
				String token = jwtService.generateToken(authRequest.getUsername());
				String role = accountService.loadUserRole(authRequest.getUsername());
				return ApiResponse.success(HttpStatus.OK.value(), new AuthResponse(token, role));

			} else {
				return ApiResponse.error(HttpStatus.UNAUTHORIZED.value(),
						ApiError.of(INVALID_CREDENTIALS, INCORRECT_USERNAME_PASSWORD));
			}
		} catch (Exception ex) {
			return ApiResponse.error(HttpStatus.UNAUTHORIZED.value(),
					ApiError.of(INVALID_CREDENTIALS, INCORRECT_USERNAME_PASSWORD));
		}
	}
}
