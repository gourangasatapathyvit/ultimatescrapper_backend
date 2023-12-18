package com.ultimateScraper.scrape.ServiceImpl;

import org.springframework.web.bind.annotation.RestController;

import com.ultimateScraper.scrape.Services.ScrapeService;

@RestController
public class ScrapeServiceImpl implements ScrapeService{

	@Override
	
	public String test() {
		// TODO Auto-generated method stub
		return "lorem";
	}

}
