package com.tus.cipher.dto;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "call_failure")
public class CallFailure {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Date
	@Column(name = "date_time", nullable = false)
	private LocalDateTime dateTime;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", nullable = false)
	private CellDetail cellDetail;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", nullable = false)
	private SubscriberDetail subscriberDetail;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", nullable = false)
	private FailureDetail failureDetail;

	public CallFailure() {}
	public CallFailure(LocalDateTime dateTime, CellDetail cellDetail, SubscriberDetail subscriberDetail,
			FailureDetail failureDetail) {
		this.dateTime = dateTime;
		this.cellDetail = cellDetail;
		this.subscriberDetail = subscriberDetail;
		this.failureDetail = failureDetail;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
