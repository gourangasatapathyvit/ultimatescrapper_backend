package com.ultimateScraper.scrape.utility.CustomLimiter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter implements Filter {

	private final RedisTemplate<String, Integer> redisTemplate;
	private final RateLimiterService rateLimiterService;

	@Autowired
	public RateLimitFilter(RedisTemplate<String, Integer> redisTemplate, RateLimiterService rateLimiterService) {
		this.redisTemplate = redisTemplate;
		this.rateLimiterService = rateLimiterService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String ipAddress = httpRequest.getRemoteAddr(); // Get client's IP address
		// Perform rate limiting logic here
		if (rateLimiterService.exceedsRateLimit(ipAddress)) {
			httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			httpResponse.getWriter().write("Rate limit exceeded");
			return;
		}
		chain.doFilter(request, response);

	}

	public boolean exceedsRateLimit(String ipAddress) {
		String key = "rate_limit:" + ipAddress;

		Long count = redisTemplate.opsForValue().increment(key, 1); // Increment key by 1

		if (count != null && count == 1) {
			// Set expiration time if the key is newly created
			redisTemplate.expire(key, 1, TimeUnit.MINUTES);
		}

		return count != null && count > 5; // Limit to 5 requests per minute
	}

	@Override
	public void init(FilterConfig filterConfig) {
		// Initialization logic
	}

	@Override
	public void destroy() {
		// Cleanup logic
	}

}
