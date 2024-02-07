package com.tus.cipher.dto.sheets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tus.cipher.dto.BaseEntity;

@Entity
@Table(name = "failure_class")
public class FailureClass extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Integer failureCode;

	@Column(nullable = false)
	private String description;

	public FailureClass() {}
	public FailureClass(Integer failureCode, String description) {
		this.failureCode = failureCode;
		this.description = description;
	}

	public Integer getFailure() {
		return failureCode;
	}

	public void setFailure(Integer failureCode) {
		this.failureCode = failureCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
