package com.ultimateScraper.scrape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ScrapeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrapeApplication.class, args);
	}

}
