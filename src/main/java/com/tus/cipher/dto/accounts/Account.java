package com.tus.cipher.dto.accounts;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
// @MappedSuperclass
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// @DiscriminatorColumn(name="role")
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

	@Column(name = "role")
	@NotBlank
    private String role;

    public Account() {

    }

	public Account(Long id, String username, String password, String role) {
		super();
		this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
