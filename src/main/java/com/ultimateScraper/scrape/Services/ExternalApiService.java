package com.ultimateScraper.scrape.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ultimateScraper.scrape.dto.GenericApiResp;

public interface ExternalApiService {
//	CompletableFuture<List<GenericApiResp>> getPirateBayRes(String input);

//	CompletableFuture<List<GenericApiResp>> getYtsRes(String input);
	List<GenericApiResp> getCachedYtsRes(String input,String tmdbApiId);
	List<GenericApiResp> getCachedpirateBayRes(String input,String tmdbApiId);
	List<GenericApiResp> getCachedSnowFlRes(String input,String tmdbApiId);

}
