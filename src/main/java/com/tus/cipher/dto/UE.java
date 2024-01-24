package com.tus.cipher.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ue")
public class UE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="tac")
	private Long tac;

	@Column(name="marketing_name")
	private String marketingName;

	@Column(name="manufacturer")
	private String manufacturer;

	@Column(name="access_capability")
	private String accessCapability;

	public UE() {}

	public UE(Long tac, String marketingName, String manufacturer, String accessCapability) {
		this.tac = tac;
		this.marketingName = marketingName;
		this.manufacturer = manufacturer;
		this.accessCapability = accessCapability;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
