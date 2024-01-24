package com.tus.cipher.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "failure_class")
public class FailureClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="failure_code")
	private Long failureCode;

	@Column(name="description")
	private String description;

	public FailureClass() {}
	public FailureClass(Long failureCode, String description) {
		this.failureCode = failureCode;
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFailure() {
		return failureCode;
	}
	public void setFailure(Long failureCode) {
		this.failureCode = failureCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
