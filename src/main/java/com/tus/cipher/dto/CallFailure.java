package com.tus.cipher.dto;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "call_failure")
public class CallFailure extends BaseEntity {

	// Date
	@Column(name = "date_time", nullable = false)
	private LocalDateTime dateTime;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", nullable = false)
	private FailureDetail failureDetail;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", nullable = false)
	private CellDetail cellDetail;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", nullable = false)
	private SubscriberDetail subscriberDetail;

	public CallFailure() {
	}

	public CallFailure(LocalDateTime dateTime, FailureDetail failureDetail, CellDetail cellDetail,
			SubscriberDetail subscriberDetail) {
		this.dateTime = dateTime;
		this.cellDetail = cellDetail;
		this.subscriberDetail = subscriberDetail;
		this.failureDetail = failureDetail;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public CellDetail getCellDetail() {
		return cellDetail;
	}

	public void setCellDetail(CellDetail cellDetail) {
		this.cellDetail = cellDetail;
	}

	public SubscriberDetail getSubscriberDetail() {
		return subscriberDetail;
	}

	public void setSubscriberDetail(SubscriberDetail subscriberDetail) {
		this.subscriberDetail = subscriberDetail;
	}

	public FailureDetail getFailureDetail() {
		return failureDetail;
	}

	public void setFailureDetail(FailureDetail failureDetail) {
		this.failureDetail = failureDetail;
	}
}
