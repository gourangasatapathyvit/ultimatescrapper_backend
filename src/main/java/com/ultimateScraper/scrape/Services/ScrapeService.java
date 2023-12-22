package com.ultimateScraper.scrape.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.ultimateScraper.scrape.dto.GenericApiResp;
import com.ultimateScraper.scrape.dto.RequestBodyParam;

public interface ScrapeService {

	@GetMapping("/test")
	public String test();


//	public CompletableFuture<List<GenericApiResp>> getAllRes(@RequestBody RequestBodyParam searchTerm);

//	@GetMapping("/getYtsRes/{input}")
//	public CompletableFuture<List<GenericApiResp>> getYtsRes(@PathVariable("input") String input);

//	@GetMapping("/getPirateBayRes/{input}")
//	public CompletableFuture<List<GenericApiResp>> getPirateBayRes(@PathVariable("input") String input);

}
