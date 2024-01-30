package com.tus.cipher.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// TODO: Add Index's to speed up Querying
// TODO: ADD ManyToOne mappings

@SuppressWarnings("serial")
@Entity
@Table(name = "call_failure")
public class CallFailure extends BaseEntity {

	// Date
	@Column(name = "date_time", nullable = false)
	private LocalDateTime dateTime;

	// Event ID
	@Column(nullable = false)
	private Integer eventId;

	// Cause Code
	@Column(nullable = false)
	private Integer causeCode;

	// Failure Class
	@Column(nullable = false)
	private Integer failureCode;

	// Duration
	@Column(nullable = false)
	private Integer duration;

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

	public CallFailure(LocalDateTime dateTime, Integer eventId, Integer causeCode, Integer failureCode, 			//NOSONAR
			Integer duration, Integer cellId, Long tac, Integer mcc, Integer mnc, String neVersion, Long imsi,
			Long hier3Id, Long hier32Id, Long hier321Id) {
		this.dateTime = dateTime;
		this.eventId = eventId;
		this.causeCode = causeCode;
		this.failureCode = failureCode;
		this.duration = duration;
		this.cellId = cellId;
		this.tac = tac;
		this.mcc = mcc;
		this.mnc = mnc;
		this.neVersion = neVersion;
		this.imsi = imsi;
		this.hier3Id = hier3Id;
		this.hier32Id = hier32Id;
		this.hier321Id = hier321Id;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(Integer causeCode) {
		this.causeCode = causeCode;
	}

	public Integer getFailureCode() {
		return failureCode;
	}

	public void setFailureCode(Integer failureCode) {
		this.failureCode = failureCode;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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
