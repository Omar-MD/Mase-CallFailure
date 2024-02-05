package com.tus.cipher.dto.sheets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tus.cipher.dto.BaseEntity;

@Entity
@Table(name = "mcc_mnc")
public class MccMnc extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Integer mcc;

	@Column(nullable = false)
	private Integer mnc;

	@Column(nullable = false)
	private String country;

	@Column(nullable = false)
	private String operator;

	public MccMnc() {}
	public MccMnc(Integer mcc, Integer mnc, String country, String operator) {
		this.mcc = mcc;
		this.mnc = mnc;
		this.country = country;
		this.operator = operator;
	}

	public Integer getMccId() {
		return mcc;
	}

	public void setMccId(Integer mcc) {
		this.mcc = mcc;
	}

	public Integer getMncId() {
		return mnc;
	}

	public void setMncId(Integer mnc) {
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
