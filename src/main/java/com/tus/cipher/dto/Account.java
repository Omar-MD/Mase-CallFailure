package com.tus.cipher.dto;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	private String role;

	public Account() {
	}

	public Account(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
