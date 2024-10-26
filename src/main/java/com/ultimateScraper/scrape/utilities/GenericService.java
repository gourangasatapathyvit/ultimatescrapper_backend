package com.ultimateScraper.scrape.utilities;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
@EnableScheduling
public class GenericService {
	private static final Logger logger = LogManager.getLogger(GenericService.class);
	@Value("${ratelimiter.limit-for-period}")
	private String limitForPeriod;
	private final RedisTemplate<String, Integer> redisTemplate;
	private final HttpClient httpClient;

	@Autowired
	public GenericService(RedisTemplate<String, Integer> redisTemplate,HttpClient httpClient) {
		this.redisTemplate = redisTemplate;
		this.httpClient = httpClient;
	}
	public boolean exceedsRateLimit(String ipAddress) {
		String key = "rate_limit:" + ipAddress;

		Long count = redisTemplate.opsForValue().increment(key, 1); // Increment key by 1

		if (count != null && count == 1) {
			// Set expiration time if the key is newly created
			redisTemplate.expire(key, 1, TimeUnit.MINUTES);
		}

		return count != null && count > Long.parseLong(limitForPeriod);
	}
	@CacheEvict(allEntries = true, value = { "*" })
	@Scheduled(fixedRateString = "${auto.refresh.intervalInMillis}")
	public void evictAllCacheValues() {
//		TODO - implement using api call (custom clear)
		logger.info("{}","lorem CacheEvict final change");
		Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushAll();
	}

	public String converTime(Long unixTime) {
		if (unixTime == null) {
			return "";
		}
		Instant instant = Instant.ofEpochSecond(unixTime);
		// Convert Instant to LocalDateTime in UTC
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
		// Format LocalDateTime to a specific pattern (optional)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
	}

	public boolean readTextFile(String inputQuery) throws IOException {
		try (InputStream is = getClass().getResourceAsStream("/blocklist.txt")) {
			if (is == null) {
				throw new IOException("blocklist.txt not found in resources directory");
			}
			// Use a Scanner to efficiently read the file line by line
			Scanner scanner = new Scanner(is);
			while (scanner.hasNextLine()) {
				String word = scanner.nextLine();
				String escapedWord = Pattern.quote(word);
				Pattern pattern = Pattern.compile("\\b" + escapedWord + "\\b", Pattern.CASE_INSENSITIVE);
				if (pattern.matcher(inputQuery).find()) {
					return true;
				}
			}
		}
		return false;
	}

	public String fetchURL(String urlString, List<Map.Entry<String, String>> headers) {
		try {
			HttpGet request = new HttpGet(urlString);
//			for (Map.Entry<String, String> header : headers) {
//				request.addHeader(header.getKey(), header.getValue());
//			}
			return httpClient.execute(request, httpResponse -> {
				StringBuilder response = new StringBuilder();
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))) {
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					return response.toString();
				}
			});
		} catch (IOException e) {
			logger.error("Error at: {} {}","fetchURL",e);
			throw new RuntimeException("Failed to fetch URL: " + urlString, e);
		}
	}

	public List<String> getIframes(List<String> existingIframeList,String baseUrl,String source){
		try{
			switch (source){
				case "2embed":
					Document document = Jsoup.connect(baseUrl).get();
					Element embedElement = document.getElementById("embed-content");
					if (embedElement != null && !existingIframeList.contains(embedElement.text())) {
						existingIframeList.add(embedElement.text());
					}
				default:
					break;
			}
		}
		catch (IOException ignored){
			logger.error("Error at: {} - at function: {} - not able to fetch data", this.getClass().getSimpleName(), "parsing problem at getIframes()");
		}
		return existingIframeList;
	}
}
