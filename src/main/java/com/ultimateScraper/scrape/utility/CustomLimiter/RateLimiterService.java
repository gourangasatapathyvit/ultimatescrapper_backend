package com.ultimateScraper.scrape.utility.CustomLimiter;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

	@Value("${resilience4j.ratelimiter.configs.getAllRes.limit-for-period}")
	private String limitForPeriod;

	private final RedisTemplate<String, Integer> redisTemplate;

	@Autowired
	public RateLimiterService(RedisTemplate<String, Integer> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public boolean exceedsRateLimit(String ipAddress) {
		String key = "rate_limit:" + ipAddress;

		Long count = redisTemplate.opsForValue().increment(key, 1); // Increment key by 1

		if (count != null && count == 1) {
			// Set expiration time if the key is newly created
			redisTemplate.expire(key, 1, TimeUnit.MINUTES);
		}

		return count != null && count > Long.valueOf(limitForPeriod); // Limit to 5 requests per minute
	}
}
