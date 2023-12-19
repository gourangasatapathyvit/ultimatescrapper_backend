package com.ultimateScraper.scrape.ServiceImpl;

import java.time.Duration;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ultimateScraper.scrape.Services.ScrapeService;
import com.ultimateScraper.scrape.dto.ApiResponse;
import com.ultimateScraper.scrape.dto.ReqDto;
import com.ultimateScraper.scrape.dto.RequestBodyParam;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class ScrapeServiceImpl implements ScrapeService {

	@Value("${external.api.general.url}") // Inject the URL from properties
	private String apiUrl;

	@Value("${resilience4j.ratelimiter.configs.getAllRes.limit-for-period}")
	private int limitForPeriod;

	@Value("${resilience4j.ratelimiter.configs.getAllRes.limit-refresh-period}")
	private Duration limitRefreshPeriod;

	@Value("${resilience4j.ratelimiter.configs.getAllRes.timeout-duration}")
	private Duration timeoutDuration;

	private RestTemplate restTemplate;

	@Override
	public String test() {
		// TODO Auto-generated method stub
		return "lorem";
	}

	public ScrapeServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	@RateLimiter(name = "getAllRes", fallbackMethod = "fallbackMethodForGetAllRes")
	public ApiResponse getAllRes(RequestBodyParam searchTerm) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Create your request body
		ReqDto requestBody = new ReqDto();
		requestBody.setExclude_categories(Collections.emptyList());
		requestBody.setInclude_categories(Collections.emptyList());
		requestBody.setInclude_sites(Collections.emptyList());
		requestBody.setExclude_sites(Collections.emptyList());
		requestBody.setSearch_term(searchTerm.getInputQuery());

		HttpEntity<ReqDto> requestEntity = new HttpEntity<>(requestBody, headers);

		ApiResponse response = restTemplate.postForObject(apiUrl, requestEntity, ApiResponse.class);

		System.out.println(response);

		return response;
	}

	public ApiResponse fallbackMethodForGetAllRes(RequestBodyParam searchTerm, Throwable throwable) {
		System.out.println("lorem");
		return null;
		// Handle fallback logic here
	}

}
