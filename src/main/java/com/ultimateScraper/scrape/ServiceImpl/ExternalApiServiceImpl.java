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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
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

	private final RestTemplate restTemplate;
	private final GenericService genericService;

	public ExternalApiServiceImpl(RestTemplate restTemplate, GenericService genericService) {
		this.restTemplate = restTemplate;
		this.genericService = genericService;
	}

	
	/*
	 * @Override
	 * 
	 * @Async("taskExecutor")
	 * 
	 * @Cacheable(value = "getPirateBayRes", key = "#input") public
	 * CompletableFuture<List<GenericApiResp>> getPirateBayRes(String input) {
	 * return CompletableFuture.supplyAsync(() -> { List<GenericApiResp> allDatas =
	 * new ArrayList<>(); DecimalFormat df = new DecimalFormat("#.##");
	 * 
	 * ResponseEntity<String> responseEntity =
	 * restTemplate.getForEntity(pirateBayUrl + input, String.class); String
	 * responseBody = responseEntity.getBody(); ObjectMapper mapper = new
	 * ObjectMapper();
	 * 
	 * try { List<PirateBay> response = mapper.readValue(responseBody, new
	 * TypeReference<List<PirateBay>>() { }); if (response != null) { for (PirateBay
	 * val : response) { GenericApiResp apiResp = new GenericApiResp();
	 * 
	 * if (genericService.readTextFile(val.getName())) { continue; }
	 * apiResp.setName(val.getName());
	 * 
	 * String magnetLink = !val.getInfo_hash().isEmpty() ? "magnet:?xt=urn:btih:" +
	 * val.getInfo_hash() : "magnet:?xt=urn:btih:";
	 * apiResp.setMagnetLink(magnetLink);
	 * 
	 * double sizeInGB = (double) Long.parseLong(val.getSize()) / (1024 * 1024 *
	 * 1024); String formattedSize = df.format(sizeInGB);
	 * apiResp.setSize(formattedSize + " " + "GB");
	 * 
	 * apiResp.setSeed(!val.getSeeders().isEmpty() ?
	 * Integer.valueOf(val.getSeeders()) : 0);
	 * apiResp.setLeech(!val.getLeechers().isEmpty() ?
	 * Integer.valueOf(val.getLeechers()) : 0); apiResp.setUploader(PIRATEBAY);
	 * apiResp.setDownLoadLink("");
	 * 
	 * apiResp.setDate(genericService .converTime(!val.getAdded().isEmpty() ?
	 * Long.valueOf(val.getAdded()) : null));
	 * 
	 * // pending - as we have imdb id, fetch image link from IMDB
	 * apiResp.setImage(""); allDatas.add(apiResp); }
	 * 
	 * }
	 * 
	 * } catch (Exception e) { return Collections.emptyList(); }
	 * 
	 * return allDatas;
	 * 
	 * }); }
	 */
	 

	/*
	 * @Override
	 * 
	 * @Async("taskExecutor")
	 * 
	 * @Cacheable(value = "getYtsRes", key = "#input") public
	 * CompletableFuture<List<GenericApiResp>> getYtsRes(String input) {
	 * 
	 * return CompletableFuture.supplyAsync(() -> { List<GenericApiResp> allDatas =
	 * new ArrayList<>();
	 * 
	 * try {
	 * 
	 * Yts response = restTemplate.getForObject(YtsUrl + input, Yts.class);
	 * 
	 * List<YtsMovie> data = response.getData().getMovies();
	 * 
	 * if (data != null) { for (YtsMovie val : data) { GenericApiResp apiResp = new
	 * GenericApiResp(); apiResp.setName(val.getTitle_english());
	 * 
	 * String magnetLink = !val.getTorrents().isEmpty() ? "magnet:?xt=urn:btih:" +
	 * val.getTorrents().get(0).getHash() : "magnet:?xt=urn:btih:";
	 * 
	 * // Pending: Logic for getting all torrents and showing in dropdown design
	 * 
	 * apiResp.setMagnetLink(magnetLink);
	 * apiResp.setSize(!val.getTorrents().isEmpty() ?
	 * val.getTorrents().get(0).getSize() : "0");
	 * apiResp.setSeed(!val.getTorrents().isEmpty() ?
	 * val.getTorrents().get(0).getSeeds() : 0);
	 * apiResp.setLeech(!val.getTorrents().isEmpty() ?
	 * val.getTorrents().get(0).getPeers() : 0); apiResp.setUploader(YTS);
	 * apiResp.setDownLoadLink(!val.getTorrents().isEmpty() ?
	 * val.getTorrents().get(0).getUrl() : "");
	 * apiResp.setDate(genericService.converTime( !val.getTorrents().isEmpty() ?
	 * val.getTorrents().get(0).getDate_uploaded_unix() : null));
	 * apiResp.setImage(val.getLarge_cover_image()); allDatas.add(apiResp); }
	 * 
	 * }
	 * 
	 * return allDatas;
	 * 
	 * } catch (Exception e) { return Collections.emptyList(); }
	 * 
	 * }); }
	 */

	@Cacheable(value = "getYtsRes", key = "#input")
	public List<GenericApiResp> getCachedYtsRes(String input) {
//		we need to return List<GenericApiResp>  instead CompletableFuture<List<GenericApiResp>> as serialize issue in redis in springboot at 2.7.5

		try {
			CompletableFuture<List<GenericApiResp>> completableFuture = getYtsResAsync(input);
			return completableFuture.get(); // Wait for completion and return result
		} catch (Exception e) {
			return Collections.emptyList();
		}

	}

	@Cacheable(value = "getPirateBayRes", key = "#input")
	public List<GenericApiResp> getCachedpirateBayRes(String input) {
//		we need to return List<GenericApiResp>  instead CompletableFuture<List<GenericApiResp>> as serialize issue in redis in java at 2.7.5

		try {
			CompletableFuture<List<GenericApiResp>> completableFuture = getpirateBayResAsync(input);
			return completableFuture.get(); // Wait for completion and return result
		} catch (Exception e) {
			return Collections.emptyList();
		}

	}

	@Async("taskExecutor")
	public CompletableFuture<List<GenericApiResp>> getYtsResAsync(String input) {
		return CompletableFuture.supplyAsync(() -> fetchYtsData(input));
	}

	@Async("taskExecutor")
	public CompletableFuture<List<GenericApiResp>> getpirateBayResAsync(String input) {
		return CompletableFuture.supplyAsync(() -> fetchpirateBayData(input));
	}

	private List<GenericApiResp> fetchYtsData(String input) {
		List<GenericApiResp> allDatas = new ArrayList<>();

		try {
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
					apiResp.setUploader("YTS"); // Assuming YTS is a constant or defined somewhere
					apiResp.setDownLoadLink(!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getUrl() : "");
					apiResp.setDate(genericService.converTime(
							!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getDate_uploaded_unix() : null));
					apiResp.setImage(val.getLarge_cover_image());
					allDatas.add(apiResp);
				}
			}

			return allDatas;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	private List<GenericApiResp> fetchpirateBayData(String input) {
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

					if (genericService.readTextFile(val.getName())) {
						continue;
					}
					apiResp.setName(val.getName());

					String magnetLink = !val.getInfo_hash().isEmpty() ? "magnet:?xt=urn:btih:" + val.getInfo_hash()
							: "magnet:?xt=urn:btih:";
					apiResp.setMagnetLink(magnetLink);

					double sizeInGB = (double) Long.parseLong(val.getSize()) / (1024 * 1024 * 1024);
					String formattedSize = df.format(sizeInGB);
					apiResp.setSize(formattedSize + " " + "GB");

					apiResp.setSeed(!val.getSeeders().isEmpty() ? Integer.parseInt(val.getSeeders()) : 0);
					apiResp.setLeech(!val.getLeechers().isEmpty() ? Integer.parseInt(val.getLeechers()) : 0);
					apiResp.setUploader(PIRATEBAY);
					apiResp.setDownLoadLink("");

					apiResp.setDate(genericService
							.converTime(!val.getAdded().isEmpty() ? Long.valueOf(val.getAdded()) : null));

					// pending - as we have imdb id, fetch image link from IMDB
					apiResp.setImage("");
					allDatas.add(apiResp);
				}

			}

		} catch (Exception e) {
			return Collections.emptyList();
		}

		return allDatas;
	}

}
