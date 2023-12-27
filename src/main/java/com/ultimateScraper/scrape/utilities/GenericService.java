package com.ultimateScraper.scrape.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
@EnableScheduling
public class GenericService {

	private static final Logger logger = LogManager.getLogger(GenericService.class);

	@Value("${ratelimiter.limit-for-period}")
	private String limitForPeriod;

	private final RedisTemplate<String, Integer> redisTemplate;

	@Autowired
	public GenericService(RedisTemplate<String, Integer> redisTemplate) {
		this.redisTemplate = redisTemplate;
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

	@SuppressWarnings("deprecation")
	@CacheEvict(allEntries = true, value = { "getYtsResp" })
	@Scheduled(fixedRateString = "${auto.refresh.intervalInMillis}")
	public void evictAllCacheValues() {
		logger.info("{}","lorem CacheEvict");
		redisTemplate.getConnectionFactory().getConnection().flushAll();
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

	public Boolean readTextFile(String inputQuery) {
		
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/blocklist.txt"))) {
			String word;
			while ((word = br.readLine()) != null) {
				String escapedString = Pattern.quote(word);
				Pattern pattern = Pattern.compile("\\b" + escapedString + "\\b", Pattern.CASE_INSENSITIVE);
				
				if (pattern.matcher(inputQuery).find()) {
					return true; 
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
}
