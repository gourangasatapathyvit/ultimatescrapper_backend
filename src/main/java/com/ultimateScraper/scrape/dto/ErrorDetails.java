package com.ultimateScraper.scrape.dto;

import java.time.LocalDate;

public class ErrorDetails {
	private LocalDate timeStamp;
	private String medssage;
	private String details;
	private Integer errorCode;

	

	public ErrorDetails(LocalDate timeStamp, String medssage, String details, Integer errorCode) {
		super();
		this.timeStamp = timeStamp;
		this.medssage = medssage;
		this.details = details;
		this.errorCode = errorCode;
	}

	public LocalDate getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDate timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMedssage() {
		return medssage;
	}

	public void setMedssage(String medssage) {
		this.medssage = medssage;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	

}
