package com.ultimateScraper.scrape.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ultimateScraper.scrape.Services.AsyncService;
import com.ultimateScraper.scrape.Services.ExternalApiService;
import com.ultimateScraper.scrape.dto.GenericApiResp;
import com.ultimateScraper.scrape.dto.RequestBodyParam;
import com.ultimateScraper.scrape.utilities.FilterContent;
import com.ultimateScraper.scrape.utilities.GenericService;

@Service
public class AsyncServiceImpl implements AsyncService {

	@Value("${filter.content}")
	private String filterContent;

	private GenericService genericService;
	private ExternalApiService externalApiService;

	public AsyncServiceImpl(GenericService genericService, ExternalApiService externalApiService) {
		this.genericService = genericService;
		this.externalApiService = externalApiService;
	}

	@Override
	@Async("taskExecutor")
	public CompletableFuture<List<GenericApiResp>> invokeGetAllRes(RequestBodyParam searchTerm) {
		if (genericService.readTextFile(searchTerm.getInputQuery())) {
			return CompletableFuture.failedFuture(new FilterContent(filterContent));
		}

		CompletableFuture<List<GenericApiResp>> ytsObj = CompletableFuture.completedFuture(new ArrayList<>());
		CompletableFuture<List<GenericApiResp>> pirateBayObj = CompletableFuture.completedFuture(new ArrayList<>());

		for (String val : searchTerm.getSource()) {
			if (val.equalsIgnoreCase("yts")) {
				ytsObj = externalApiService.getYtsRes(searchTerm.getInputQuery()).exceptionally(ex -> {
					return Collections.emptyList(); //

				});
			}

			if (val.toLowerCase().equals("piratebay")) {
				pirateBayObj = externalApiService.getPirateBayRes(searchTerm.getInputQuery()).exceptionally(ex -> {
					return Collections.emptyList(); //
				});
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
