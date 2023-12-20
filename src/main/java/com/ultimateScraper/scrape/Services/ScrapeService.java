package com.ultimateScraper.scrape.Services;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ultimateScraper.scrape.dto.ApiResponse;
import com.ultimateScraper.scrape.dto.RequestBodyParam;
import com.ultimateScraper.scrape.dto.Yts;

@CrossOrigin(origins = "http://localhost:8080")
public interface ScrapeService {

	@GetMapping("/test")
	public String test();

	@PostMapping("/getAllRes")
	public ApiResponse getAllRes(@RequestBody RequestBodyParam searchTerm);

	@GetMapping("/getYtsRes/{input}")
	public Yts getYtsRes(@PathVariable("input") String input);

}
