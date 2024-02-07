package com.tus.cipher.dto.sheets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tus.cipher.dto.BaseEntity;

@Entity
@Table(name = "ue")
public class Ue extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Long tac;

	@Column(nullable = false)
	private String marketingName;

	@Column(nullable = false)
	private String manufacturer;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String accessCapability;

	public Ue() {}
	public Ue(Long tac, String marketingName, String manufacturer, String accessCapability) {
		this.tac = tac;
		this.marketingName = marketingName;
		this.manufacturer = manufacturer;
		this.accessCapability = accessCapability;
	}

	public Long getTac() {
		return tac;
	}

	public void setTac(Long tac) {
		this.tac = tac;
	}

	public String getMarketingName() {
		return marketingName;
	}

	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getAccessCapability() {
		return accessCapability;
	}

	public void setAccessCapability(String accessCapability) {
		this.accessCapability = accessCapability;
	}
}