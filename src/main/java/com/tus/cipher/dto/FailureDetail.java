package com.tus.cipher.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "failure_detail")
public class FailureDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Event ID
	@Column(name = "event_id")
	private Integer eventId;

	// Cause Code
	@Column(name = "cause_code")
	private Integer causeCode;

	// Failure Class
	@Column(name = "failure_code")
	private Integer failureCode;

	// Duration
	@Column(name = "duration")
	private Integer duration;

	public FailureDetail() {}
	public FailureDetail(Integer eventId, Integer causeCode, Integer failureCode, Integer duration) {
		super();
		this.eventId = eventId;
		this.causeCode = causeCode;
		this.failureCode = failureCode;
		this.duration = duration;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
