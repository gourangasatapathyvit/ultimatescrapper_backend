package com.ultimateScraper.scrape.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class ErrorDetails {
    private LocalDate timeStamp;
    private String message;
    private String details;
    private Integer errorCode;

    @JsonIgnore
    public ErrorDetails(LocalDate timeStamp, String message, String details, Integer errorCode) {
        super();
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
        this.errorCode = errorCode;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
