package com.ultimateScraper.scrape.ServiceImpl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ultimateScraper.scrape.Services.AsyncService;
import com.ultimateScraper.scrape.Services.ScrapeService;
import com.ultimateScraper.scrape.dto.GenericApiResp;
import com.ultimateScraper.scrape.dto.RequestBodyParam;

@RestController
@EnableCaching
@RequestMapping("/scrape")
public class ScrapeServiceImpl implements ScrapeService {
	private final AsyncService asyncService;

	public ScrapeServiceImpl(AsyncService asyncService) {
		this.asyncService = asyncService;
	}

	@Override
	public String test() {
		return "lorem";
	}

	/*
	 * public CompletableFuture<List<GenericApiResp>> getAllRes(RequestBodyParam
	 * searchTerm) { return asyncService.invokeGetAllRes(searchTerm);
	 * 
	 * }
	 */

	public List<GenericApiResp> getAllRes(RequestBodyParam searchTerm) {
		return asyncService.invokeGetAllRes(searchTerm);
		
	}

}
