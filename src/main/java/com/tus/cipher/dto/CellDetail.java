package com.tus.cipher.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "cell_detail")
public class CellDetail extends BaseEntity {

	// Cell ID
	@Column(nullable = false)
	private Integer cellId;

	// UE Type
	@Column(nullable = false)
	private Long tac;

	// Market
	@Column(nullable = false)
	private Integer mcc;

	// Operator
	@Column(nullable = false)
	private Integer mnc;

	// NE version
	@Column(nullable = false)
	private String neVersion;

	public CellDetail() {}
	public CellDetail(Integer cellId, Long tac, Integer mcc, Integer mnc, String neVersion) {
		this.cellId = cellId;
		this.tac = tac;
		this.mcc = mcc;
		this.mnc = mnc;
		this.neVersion = neVersion;
	}

	public Integer getCellId() {
		return cellId;
	}
	public void setCellId(Integer cellId) {
		this.cellId = cellId;
	}
	public Long getTac() {
		return tac;
	}
	public void setTac(Long tac) {
		this.tac = tac;
	}
	public Integer getMcc() {
		return mcc;
	}
	public void setMcc(Integer mcc) {
		this.mcc = mcc;
	}
	public Integer getMnc() {
		return mnc;
	}
	public void setMnc(Integer mnc) {
		this.mnc = mnc;
	}
	public String getNeVersion() {
		return neVersion;
	}
	public void setNeVersion(String neVersion) {
		this.neVersion = neVersion;
	}
}
