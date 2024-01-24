package com.tus.cipher.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mcc_mnc")
public class MccMnc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="mcc")
	private Long mcc;

	@Column(name="mnc")
	private Long mnc;

	@Column(name="country")
	private String country;

	@Column(name="operator")
	private String operator;

	public MccMnc() {}

	public MccMnc(Long mcc, Long mnc, String country, String operator) {
		this.mcc = mcc;
		this.mnc = mnc;
		this.country = country;
		this.operator = operator;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMccId() {
		return mcc;
	}

	public void setMccId(Long mcc) {
		this.mcc = mcc;
	}

	public Long getMncId() {
		return mnc;
	}

	public void setMncId(Long mnc) {
		this.mnc = mnc;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
