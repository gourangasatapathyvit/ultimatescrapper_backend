package com.ultimateScraper.scrape;

import java.net.http.HttpClient;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
@EnableCaching
public class AppConfig {


	  @Bean
	    public RestTemplate restTemplate() {
	        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
	        requestFactory.setConnectTimeout(5000); // 5 seconds connection timeout
	        requestFactory.setReadTimeout(5000); // 5 seconds socket timeout

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
	public Executor taskExecutor() {
		return Executors.newFixedThreadPool(10);
	}

}
