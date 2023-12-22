package com.ultimateScraper.scrape.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ultimateScraper.scrape.Services.ExternalApiService;
import com.ultimateScraper.scrape.Services.ScrapeService;
import com.ultimateScraper.scrape.dto.GenericApiResp;
import com.ultimateScraper.scrape.dto.RequestBodyParam;
import com.ultimateScraper.scrape.utilities.FilterContent;
import com.ultimateScraper.scrape.utilities.GenericService;

@RestController
@EnableCaching
@CrossOrigin(origins = "http://localhost:8080")
public class ScrapeServiceImpl {

	@Value("${filter.content}")
	private String filterContent;

	private GenericService genericService;
	private ExternalApiService externalApiService;

	public ScrapeServiceImpl(GenericService genericService, ExternalApiService externalApiService) {
		this.genericService = genericService;
		this.externalApiService = externalApiService;
	}

	@PostMapping("/getAllRes")
	@Async("taskExecutor")
	public CompletableFuture<List<GenericApiResp>> getAllRes(@RequestBody RequestBodyParam searchTerm) {
		if (genericService.readTextFile(searchTerm.getInputQuery())) {
			return CompletableFuture.failedFuture(new FilterContent(filterContent));
		}

		CompletableFuture<List<GenericApiResp>> ytsObj = CompletableFuture.completedFuture(new ArrayList<>());
		CompletableFuture<List<GenericApiResp>> pirateBayObj = CompletableFuture.completedFuture(new ArrayList<>());

		for (String val : searchTerm.getSource()) {
			if (val.equalsIgnoreCase("yts")) {
				ytsObj = externalApiService.getYtsRes(searchTerm.getInputQuery());
			}

			if (val.toLowerCase().equals("piratebay")) {
				pirateBayObj = externalApiService.getPirateBayRes(searchTerm.getInputQuery());
			}
		}

		return ytsObj.thenCombine(pirateBayObj, (ytsRes, pirateBayRes) -> {
			List<GenericApiResp> combinedResults = new ArrayList<>();
			combinedResults.addAll(ytsRes);
			combinedResults.addAll(pirateBayRes);
			return combinedResults;
		});

	}

}
