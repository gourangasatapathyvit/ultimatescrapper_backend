package com.ultimateScraper.scrape.ServiceImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultimateScraper.scrape.Plugins.SubDtos.YtsMovie;
import com.ultimateScraper.scrape.Services.ExternalApiService;
import com.ultimateScraper.scrape.dto.GenericApiResp;
import com.ultimateScraper.scrape.dto.PirateBay;
import com.ultimateScraper.scrape.dto.Yts;
import com.ultimateScraper.scrape.utilities.GenericService;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {
	
	private final static String YTS = "YTS";
	private final static String PIRATEBAY = "Pirate Bay";
	
	@Value("${external.api.yts.url}")
	private String YtsUrl;

	@Value("${external.api.pirateBay.url}")
	private String pirateBayUrl;
	
	private RestTemplate restTemplate;
	private GenericService genericService;

	public ExternalApiServiceImpl(RestTemplate restTemplate, GenericService genericService) {
		this.restTemplate = restTemplate;
		this.genericService = genericService;
	}

	@Override
	@Cacheable(value = "getPirateBayRes", key = "#input")
	public CompletableFuture<List<GenericApiResp>> getPirateBayRes(String input) {
		return CompletableFuture.supplyAsync(() -> {
			List<GenericApiResp> allDatas = new ArrayList<>();
			DecimalFormat df = new DecimalFormat("#.##");

			ResponseEntity<String> responseEntity = restTemplate.getForEntity(pirateBayUrl + input, String.class);
			String responseBody = responseEntity.getBody();
			ObjectMapper mapper = new ObjectMapper();

			try {
				List<PirateBay> response = mapper.readValue(responseBody, new TypeReference<List<PirateBay>>() {
				});
				if (response != null) {
					for (PirateBay val : response) {
						GenericApiResp apiResp = new GenericApiResp();

//						if (genericService.readTextFile(val.getName())) {
//							continue;
//						}
						apiResp.setName(val.getName());

						String magnetLink = !val.getInfo_hash().isEmpty() ? "magnet:?xt=urn:btih:" + val.getInfo_hash()
								: "magnet:?xt=urn:btih:";
						apiResp.setMagnetLink(magnetLink);

						double sizeInGB = (double) Long.parseLong(val.getSize()) / (1024 * 1024 * 1024);
						String formattedSize = df.format(sizeInGB);
						apiResp.setSize(formattedSize + " " + "GB");

						apiResp.setSeed(!val.getSeeders().isEmpty() ? Integer.valueOf(val.getSeeders()) : 0);
						apiResp.setLeech(!val.getLeechers().isEmpty() ? Integer.valueOf(val.getLeechers()) : 0);
						apiResp.setUploader(PIRATEBAY);
						apiResp.setDownLoadLink("");

						apiResp.setDate(genericService
								.converTime(!val.getAdded().isEmpty() ? Long.valueOf(val.getAdded()) : null));

						// pending - as we have imdb id, fetch image link from IMDB
						apiResp.setImage("");
						allDatas.add(apiResp);
					}

				}

			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return Collections.emptyList();
			}
			return allDatas;

		});
	}

	@Override
	@Cacheable(value = "getYtsRes", key = "#input")
	public CompletableFuture<List<GenericApiResp>> getYtsRes(String input) {

		return CompletableFuture.supplyAsync(() -> {
			List<GenericApiResp> allDatas = new ArrayList<>();

			Yts response = restTemplate.getForObject(YtsUrl + input, Yts.class);

			List<YtsMovie> data = response.getData().getMovies();

			if (data != null) {
				for (YtsMovie val : data) {
					GenericApiResp apiResp = new GenericApiResp();
					apiResp.setName(val.getTitle_english());

					String magnetLink = !val.getTorrents().isEmpty()
							? "magnet:?xt=urn:btih:" + val.getTorrents().get(0).getHash()
							: "magnet:?xt=urn:btih:";

					// Pending: Logic for getting all torrents and showing in dropdown design

					apiResp.setMagnetLink(magnetLink);
					apiResp.setSize(!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getSize() : "0");
					apiResp.setSeed(!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getSeeds() : 0);
					apiResp.setLeech(!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getPeers() : 0);
					apiResp.setUploader(YTS);
					apiResp.setDownLoadLink(!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getUrl() : "");
					apiResp.setDate(genericService.converTime(
							!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getDate_uploaded_unix() : null));
					apiResp.setImage(val.getLarge_cover_image());
					allDatas.add(apiResp);
				}

			}

			return allDatas;
		});
	}

}
