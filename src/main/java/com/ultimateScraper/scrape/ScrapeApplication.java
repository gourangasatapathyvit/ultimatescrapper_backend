package com.ultimateScraper.scrape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ScrapeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrapeApplication.class, args);
	}

}
