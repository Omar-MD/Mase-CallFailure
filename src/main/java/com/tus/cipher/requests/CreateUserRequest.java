package com.tus.cipher.requests;

import com.tus.cipher.dto.accounts.EmployeeRole;

public class CreateUserRequest {
	private String username;
	private String password;
	private EmployeeRole role;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public EmployeeRole getRole() {
		return role;
	}
	public void setRole(EmployeeRole role) {
		this.role = role;
	}
}
