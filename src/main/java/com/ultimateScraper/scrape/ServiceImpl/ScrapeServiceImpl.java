package com.ultimateScraper.scrape.ServiceImpl;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ultimateScraper.scrape.Services.ScrapeService;
import com.ultimateScraper.scrape.dto.ApiResponse;
import com.ultimateScraper.scrape.dto.ReqDto;
import com.ultimateScraper.scrape.dto.RequestBodyParam;
import com.ultimateScraper.scrape.utility.CustomLimiter.RateLimitFilter;
import com.ultimateScraper.scrape.utility.CustomLimiter.RateLimiterService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@EnableCaching
public class ScrapeServiceImpl implements ScrapeService {

	@Value("${external.api.general.url}") // Inject the URL from properties
	private String apiUrl;

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

}
