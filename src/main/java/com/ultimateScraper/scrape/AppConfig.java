package com.ultimateScraper.scrape;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
public class AppConfig {

//	@Value("${proxy.host}")
//	private String proxyHost;
//
//	@Value("${proxy.port}")
//	private int proxyPort;

	@Bean
	public RestTemplate restTemplate() {

//		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//
//		// Set proxy settings
//		if (proxyHost != null && !proxyHost.isEmpty()) {
//			requestFactory.setProxy(
//					new java.net.Proxy(java.net.Proxy.Type.HTTP, new java.net.InetSocketAddress(proxyHost, proxyPort)));
//		}
//
//		return new RestTemplate(requestFactory);
		return new RestTemplate();
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
