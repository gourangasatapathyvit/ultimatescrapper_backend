package com.ultimateScraper.scrape.Services;

import org.springframework.web.bind.annotation.GetMapping;

public interface ScrapeService {
	
	@GetMapping("/test")
	public String test();

}
