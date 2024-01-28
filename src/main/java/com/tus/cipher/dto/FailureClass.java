package com.tus.cipher.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "failure_class_table")
public class FailureClass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long failureCode;

	@Column(nullable = false)
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
