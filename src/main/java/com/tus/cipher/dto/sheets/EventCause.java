package com.tus.cipher.dto.sheets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tus.cipher.dto.BaseEntity;

@Entity
@Table(name = "event_cause")
public class EventCause extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Integer causeCode;

	@Column(nullable = false)
	private Integer eventId;

	@Column(nullable = false)
	private String description;

	public EventCause(Integer causeCode, Integer eventId, String description) {
		this.causeCode = causeCode;
		this.eventId = eventId;
		this.description = description;
	}

	public Integer getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(Integer causeCode) {
		this.causeCode = causeCode;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
