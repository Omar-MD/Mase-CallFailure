package com.tus.cipher.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cell_detail")
public class CellDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Cell ID
	@Column(nullable = false)
	private Integer cellID;

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
	public CellDetail(Integer cellID, Long tac, Integer mcc, Integer mnc, String neVersion) {
		this.cellID = cellID;
		this.tac = tac;
		this.mcc = mcc;
		this.mnc = mnc;
		this.neVersion = neVersion;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCellID() {
		return cellID;
	}
	public void setCellID(Integer cellID) {
		this.cellID = cellID;
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
