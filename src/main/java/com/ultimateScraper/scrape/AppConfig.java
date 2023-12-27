package com.ultimateScraper.scrape;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.DiscoveryClient;
import com.ultimateScraper.scrape.utilities.GenericService;
import com.ultimateScraper.scrape.utilities.RateLimitFilter;

@Configuration
@EnableAsync
@EnableCaching
public class AppConfig {

	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(15000); // 5 seconds connection timeout
		requestFactory.setReadTimeout(15000); // 5 seconds socket timeout

		return new RestTemplate(requestFactory);
	}

	@Bean
	public RedisTemplate<String, Integer> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Integer> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new GenericToStringSerializer<>(String.class));
		return template;
	}

	@Bean(name = "taskExecutor")
	public ThreadPoolTaskExecutor taskExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("async-service-taskExecutor-");
		executor.initialize();
		return executor;
	}
	
	@Bean
	public FilterRegistrationBean<RateLimitFilter> rateLimitFilter(RedisTemplate<String, Integer> redisTemplate, GenericService rateLimiterService) {
	    FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<>();

	    RateLimitFilter filter = new RateLimitFilter(redisTemplate, rateLimiterService);
	    registrationBean.setFilter(filter);

	    registrationBean.addUrlPatterns("/*"); // Specify the URL patterns to match
	    registrationBean.setOrder(1); // Set the filter order, if we have multiple filter prioritize

	    return registrationBean;
	}
	
}
