package com.ultimateScraper.scrape.utilities;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ultimateScraper.scrape.dto.ErrorDetails;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) throws Exception {
		System.out.println("exception hit");
		ErrorDetails details = new ErrorDetails(LocalDate.now(), ex.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());

		return new ResponseEntity<ErrorDetails>(details, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FilterContent.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request)
			throws Exception {
		System.out.println("filter hit");
		ErrorDetails details = new ErrorDetails(LocalDate.now(), ex.getMessage(), request.getDescription(false),HttpStatus.FORBIDDEN.value());

		return new ResponseEntity<ErrorDetails>(details, HttpStatus.FORBIDDEN);
	}

}
