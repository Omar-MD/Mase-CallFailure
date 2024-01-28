package com.tus.cipher.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "subscriber_detail")
public class SubscriberDetail extends BaseEntity {

	// IMSI ID
	@Column(nullable = false)
	private Long imsi;

	// HIER3 ID
	@Column(nullable = false)
	private Long hier3Id;

	// HIER32 ID
	@Column(nullable = false)
	private Long hier32Id;

	// HIER321 ID
	@Column(nullable = false)
	private Long hier321Id;

	public SubscriberDetail() {}
	public SubscriberDetail(Long imsi, Long hier3Id, Long hier32Id, Long hier321Id) {
		this.imsi = imsi;
		this.hier3Id = hier3Id;
		this.hier32Id = hier32Id;
		this.hier321Id = hier321Id;
	}

	public Long getImsi() {
		return imsi;
	}

	public void setImsi(Long imsi) {
		this.imsi = imsi;
	}

	public Long getHier3Id() {
		return hier3Id;
	}

	public void setHier3Id(Long hier3Id) {
		this.hier3Id = hier3Id;
	}

	public Long getHier32Id() {
		return hier32Id;
	}

	public void setHier32Id(Long hier32Id) {
		this.hier32Id = hier32Id;
	}

	public Long getHier321Id() {
		return hier321Id;
	}

	public void setHier321Id(Long hier321Id) {
		this.hier321Id = hier321Id;
	}
}
