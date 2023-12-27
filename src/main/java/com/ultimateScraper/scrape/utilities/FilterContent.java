package com.ultimateScraper.scrape.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class FilterContent extends RuntimeException {
	
	public FilterContent(String message) {
		super(message);
	}

}
