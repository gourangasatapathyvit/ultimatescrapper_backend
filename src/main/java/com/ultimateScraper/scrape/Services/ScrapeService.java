package com.ultimateScraper.scrape.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ultimateScraper.scrape.dto.GenericApiResp;
import com.ultimateScraper.scrape.dto.RequestBodyParam;

public interface ScrapeService {

	@GetMapping("/test")
    String test();
//
//	@PostMapping("/getAllRes")
//	public CompletableFuture<List<GenericApiResp>> getAllRes(@RequestBody RequestBodyParam searchTerm);

	@PostMapping("/getAllRes")
    CompletableFuture<List<GenericApiResp>> getAllRes(@RequestBody RequestBodyParam searchTerm);


}
