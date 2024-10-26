package com.ultimateScraper.scrape.ServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(AsyncServiceImpl.class);

    @Value("${filter.content}")
    private String filterContent;

    private final GenericService genericService;
    private final ExternalApiService externalApiService;

    public AsyncServiceImpl(GenericService genericService, ExternalApiService externalApiService) {
        this.genericService = genericService;
        this.externalApiService = externalApiService;
    }

    /*
     * @Override public CompletableFuture<List<GenericApiResp>>
     * invokeGetAllRes(RequestBodyParam searchTerm) { if
     * (genericService.readTextFile(searchTerm.getInputQuery())) { return
     * CompletableFuture.failedFuture(new FilterContent(filterContent)); }
     *
     * List<CompletableFuture<List<GenericApiResp>>> apiCalls = new ArrayList<>();
     *
     * for (String val : searchTerm.getSource()) {
     * CompletableFuture<List<GenericApiResp>> apiCall =
     * getApiCall(searchTerm.getInputQuery(), val);
     *
     * apiCalls.add(apiCall.exceptionally(ex -> { return Collections.emptyList();
     * }));
     *
     * } CompletableFuture<Void> allOf =
     * CompletableFuture.allOf(apiCalls.toArray(new CompletableFuture[0]));
     *
     * return allOf.thenApply( v ->
     * apiCalls.stream().map(CompletableFuture::join).flatMap(List::stream).collect(
     * Collectors.toList())); }
     */

    @Override
    @Async("taskExecutor")
    public CompletableFuture<List<GenericApiResp>> invokeGetAllRes(RequestBodyParam searchTerm) {
		List<CompletableFuture<List<GenericApiResp>>> apiCallFutures = new ArrayList<>();

		try {
			if (genericService.readTextFile(searchTerm.getInputQuery())) {
				throw new FilterContent(filterContent);
			}
		} catch (IOException e) {
			logger.error("Error at: {} {}", "invokeGetAllRes", e.getMessage());
		}

        for (String val : searchTerm.getSource()) {
            CompletableFuture<List<GenericApiResp>> apiCallFuture = CompletableFuture.supplyAsync(() ->
                    getApiCall(searchTerm.getInputQuery(), val,searchTerm.getTmdbId()));
            apiCallFutures.add(apiCallFuture);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(apiCallFutures.toArray(new CompletableFuture[0]));

        return allOf.thenApply(v ->
                        apiCallFutures.stream()
                                .map(CompletableFuture::join)
                                .flatMap(List::stream)
                                .collect(Collectors.toList()));
    }

    private List<GenericApiResp> getApiCall(String inputQuery, String apiName,String tmdbApiId) {
        if (apiName.equalsIgnoreCase("yts")) {
            return externalApiService.getCachedYtsRes(inputQuery,tmdbApiId);
        } else if (apiName.equalsIgnoreCase("piratebay")) {
            return externalApiService.getCachedpirateBayRes(inputQuery,tmdbApiId);
        } else if (apiName.equalsIgnoreCase("snowfl")) {
            return externalApiService.getCachedSnowFlRes(inputQuery,tmdbApiId);
        }
        // Add conditions for other APIs  in the future
        return Collections.emptyList();
    }

}
