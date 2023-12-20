package com.ultimateScraper.scrape.ServiceImpl;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import com.ultimateScraper.scrape.dto.Yts;
import com.ultimateScraper.scrape.utilities.RateLimitFilter;
import com.ultimateScraper.scrape.utilities.GenericService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@EnableCaching
public class ScrapeServiceImpl implements ScrapeService {

	@Value("${external.api.general.url}")
	private String apiUrl;

	@Value("${external.api.yts.url}")
	private String YtsUrl;

	private RestTemplate restTemplate;

	public ScrapeServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;

	}

	@Override
	public String test() {
		// TODO Auto-generated method stub
		return "lorem";
	}

	@Override
	@Cacheable(value = "getAllRes", key = "#searchTerm.inputQuery")
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

	@Override
	@Cacheable(value = "getYtsResp", key = "#input")
	public Yts getYtsRes(String input) {

		Yts response = restTemplate.getForObject(YtsUrl + input, Yts.class);

		return response;
	}

}
