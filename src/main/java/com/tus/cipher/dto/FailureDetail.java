package com.tus.cipher.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "failure_detail")
public class FailureDetail extends BaseEntity {

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

	public FailureDetail() {}
	public FailureDetail(Integer eventId, Integer causeCode, Integer failureCode, Integer duration) {
		super();
		this.eventId = eventId;
		this.causeCode = causeCode;
		this.failureCode = failureCode;
		this.duration = duration;
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
}
