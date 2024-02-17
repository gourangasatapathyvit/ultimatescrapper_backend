package com.ultimateScraper.scrape;

import com.google.gson.Gson;
import com.ultimateScraper.scrape.utilities.GenericService;
import com.ultimateScraper.scrape.utilities.RateLimitFilter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.example.ModuleConfigurationApp;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
@EnableCaching
@Import({ ModuleConfigurationApp.class })
public class AppConfig{
	@Bean
	public Gson gson() {
		return new Gson();
	}

	@Bean
	public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
		GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
		converter.setGson(gson);
		return converter;
	}
	@Bean
	public HttpClient httpClient() {
		return HttpClients.createDefault();
	}

	@Bean
	public RestTemplate restTemplate(GsonHttpMessageConverter gsonHttpMessageConverter) {

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(15000);
		requestFactory.setReadTimeout(15000);

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.getMessageConverters().add(gsonHttpMessageConverter);

		return restTemplate;
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
	public FilterRegistrationBean<RateLimitFilter> rateLimitFilter(GenericService rateLimiterService) {

	    FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<>();
	    RateLimitFilter filter = new RateLimitFilter(rateLimiterService);
	    registrationBean.setFilter(filter);
	    registrationBean.addUrlPatterns("/*"); // Specify the URL patterns to match
	    registrationBean.setOrder(1); // Set the filter order, if we have multiple filter prioritize
	    return registrationBean;
	}
}
