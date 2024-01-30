package com.tus.cipher.dto.accounts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

	@Column(name = "username")
	@NotBlank
    private String username;

	@Column(name = "password")
	@NotBlank
    private String password;

	@Enumerated(EnumType.STRING)
	// @NotBlank
    private EmployeeRole role;
	

    public Account() {

    }

	public Account(String username, String password, EmployeeRole role) {
		super();
		this.username = username;
		this.password = password;
        this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }
}
