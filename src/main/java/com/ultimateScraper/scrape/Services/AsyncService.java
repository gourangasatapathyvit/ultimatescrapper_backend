package com.ultimateScraper.scrape.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ultimateScraper.scrape.dto.GenericApiResp;
import com.ultimateScraper.scrape.dto.RequestBodyParam;

public interface AsyncService {
//	CompletableFuture<List<GenericApiResp>> invokeGetAllRes(RequestBodyParam searchTerm);
	CompletableFuture<List<GenericApiResp>> invokeGetAllRes(RequestBodyParam searchTerm);

}
