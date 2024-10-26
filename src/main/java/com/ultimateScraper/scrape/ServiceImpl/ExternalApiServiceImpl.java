package com.ultimateScraper.scrape.ServiceImpl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.ultimateScraper.scrape.dto.SnowFl;
import com.ultimateScraper.scrape.utilities.SnowFlCustomServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	private static final Logger logger = LogManager.getLogger(ExternalApiServiceImpl.class);

	private final static String YTS = "YTS";
	private final static String PIRATEBAY = "Pirate Bay";
	private final static String SNOWFL = "Snow Fl";

	@Value("${external.api.yts.url}")
	private String YtsUrl;

	@Value("${external.api.pirateBay.url}")
	private String pirateBayUrl;

	@Value("${external.api.pirateBay.description}")
	private String pirateBayDescription;

	@Value("${external.api.snowFl.url}")
	private String snowFlUrl;

	private final RestTemplate restTemplate;
	private final GenericService genericService;
	private final SnowFlCustomServices snowFlCustomServices;
	private static List<String> iframeCollections = new ArrayList<>();

	public ExternalApiServiceImpl(RestTemplate restTemplate, GenericService genericService, SnowFlCustomServices snowFlCustomServices) {
		this.restTemplate = restTemplate;
		this.genericService = genericService;
		this.snowFlCustomServices = snowFlCustomServices;
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
	public List<GenericApiResp> getCachedYtsRes(String input,String tmdbApiId) {
//		we need to return List<GenericApiResp>  instead CompletableFuture<List<GenericApiResp>> as serialize issue in redis in springboot at 2.7.5

		try {
			CompletableFuture<List<GenericApiResp>> completableFuture = getYtsResAsync(input,tmdbApiId);
			return completableFuture.get(); // Wait for completion and return result
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Cacheable(value = "getPirateBayRes", key = "#input")
	public List<GenericApiResp> getCachedpirateBayRes(String input,String tmdbApiId) {
//		we need to return List<GenericApiResp>  instead CompletableFuture<List<GenericApiResp>> as serialize issue in redis in java at 2.7.5

		try {
			CompletableFuture<List<GenericApiResp>> completableFuture = getpirateBayResAsync(input,tmdbApiId);
			return completableFuture.get(); // Wait for completion and return result
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	@Cacheable(value = "getCachedSnowFlRes", key = "#input")
	public List<GenericApiResp> getCachedSnowFlRes(String input,String tmdbApiId) {
		try {
			CompletableFuture<List<GenericApiResp>> completableFuture = getSnowFlAsync(input,tmdbApiId);
			return completableFuture.get(); // Wait for completion and return result
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

//	@Async("taskExecutor")
	public CompletableFuture<List<GenericApiResp>> getYtsResAsync(String input,String tmdbApiId) {
		return CompletableFuture.supplyAsync(() -> fetchYtsData(input,tmdbApiId));
	}

//	@Async("taskExecutor")
	public CompletableFuture<List<GenericApiResp>> getpirateBayResAsync(String input,String tmdbApiId) {
		return CompletableFuture.supplyAsync(() -> fetchpirateBayData(input,tmdbApiId));
	}

//	@Async("taskExecutor")
	public CompletableFuture<List<GenericApiResp>> getSnowFlAsync(String input,String tmdbApiId) {
		return CompletableFuture.supplyAsync(() -> fetchSnowFlData(input,tmdbApiId));
	}
	private List<GenericApiResp> fetchYtsData(String input,String tmdbApiId) {
		List<GenericApiResp> allDatas = new ArrayList<>();
		logger.info("baseurl generated for {} : {}  - ","YTS", YtsUrl + input);
		try {
			Yts response = restTemplate.getForObject(YtsUrl + input, Yts.class);
			List<YtsMovie> data = response != null ? response.getData().getMovies() : null;
			iframeCollections =  genericService.getIframes(iframeCollections,"https://www.2embed.skin/movie/"+tmdbApiId,"2embed");
			if (data != null) {
				for (YtsMovie val : data) {
					GenericApiResp apiResp = new GenericApiResp();
					apiResp.setName(val.getTitle_english());

					String magnetLink = !val.getTorrents().isEmpty()
							? "magnet:?xt=urn:btih:" + val.getTorrents().get(0).getHash()
							: "magnet:?xt=urn:btih:";

					// TODO: Logic for getting all torrents and showing in dropdown design

					apiResp.setMagnetLink(magnetLink);
					apiResp.setSize(!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getSize() : "0");
					apiResp.setSeed(!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getSeeds() : 0);
					apiResp.setLeech(!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getPeers() : 0);
					apiResp.setUploader(YTS); // Assuming YTS is a constant or defined somewhere
					apiResp.setDownLoadLink(!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getUrl() : "");
					apiResp.setDate(genericService.converTime(
							!val.getTorrents().isEmpty() ? val.getTorrents().get(0).getDate_uploaded_unix() : null));
					apiResp.setImage(val.getLarge_cover_image());
					apiResp.setIframes(iframeCollections);
					allDatas.add(apiResp);
				}
			}

			return allDatas;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	private List<GenericApiResp> fetchpirateBayData(String input,String tmdbApiId) {
		List<GenericApiResp> allDatas = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.##");
		logger.info("baseurl generated for {} : {}","pirateBay", pirateBayUrl + input);
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(pirateBayUrl + input, String.class);
		String responseBody = responseEntity.getBody();
		ObjectMapper mapper = new ObjectMapper();
		iframeCollections =  genericService.getIframes(iframeCollections,"https://www.2embed.skin/movie/"+tmdbApiId,"2embed");
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
					String magnetLink = !val.getInfo_hash().isEmpty() ? "magnet:?xt=urn:btih:" + val.getInfo_hash(): "";
					apiResp.setMagnetLink(magnetLink);
					double sizeInGB = (double) Long.parseLong(val.getSize()) / (1024 * 1024 * 1024);
					String formattedSize = df.format(sizeInGB);
					apiResp.setSize(formattedSize + " " + "GB");
					apiResp.setSeed(!val.getSeeders().isEmpty() ? Integer.parseInt(val.getSeeders()) : 0);
					apiResp.setLeech(!val.getLeechers().isEmpty() ? Integer.parseInt(val.getLeechers()) : 0);
					apiResp.setUploader(PIRATEBAY);
					apiResp.setIframes(iframeCollections);
					apiResp.setDownLoadLink(pirateBayDescription+val.getId());
					apiResp.setDate(genericService
							.converTime(!val.getAdded().isEmpty() ? Long.valueOf(val.getAdded()) : null));

					// TODO - as we have imdb id, fetch image link from IMDB
					apiResp.setImage("");
					allDatas.add(apiResp);
				}

			}
		} catch (Exception e) {
			return Collections.emptyList();
		}
		return allDatas;
	}

	private List<GenericApiResp> fetchSnowFlData(String input,String tmdbApiId) {
//		TODO - add header
		List<Map.Entry<String, String>> headers = new ArrayList<>();
		List<GenericApiResp> allDatas = new ArrayList<>();
		String token = snowFlCustomServices.fetchToken(snowFlUrl, headers);
		String query = snowFlCustomServices.generateQuery(input, token, snowFlUrl);
		logger.info("baseurl generated for {} : {}  - ","SnowFl", query);
		iframeCollections =  genericService.getIframes(iframeCollections,"https://www.2embed.skin/embed/"+tmdbApiId,"2embed");

		try {
			String resp = genericService.fetchURL(query, headers);
			Gson gson = new Gson();
			SnowFl[] response = gson.fromJson(resp, SnowFl[].class);
			if (response != null) {
				for (SnowFl val : response) {
					GenericApiResp apiResp = new GenericApiResp();
					if (genericService.readTextFile(val.getName())) {
						continue;
					}
					apiResp.setName(val.getName());
					String magnetLink = val.getMagnet() != null && !val.getMagnet().isEmpty() ? val.getMagnet() : "";
					apiResp.setMagnetLink(magnetLink);
					apiResp.setSize(val.getSize());
					apiResp.setSeed(val.getSeeder());
					apiResp.setLeech(val.getLeecher());
					apiResp.setUploader(SNOWFL);
					apiResp.setDownLoadLink(val.getUrl());
					apiResp.setDate(val.getAge());
					apiResp.setImage("");
					apiResp.setIframes(iframeCollections);
					allDatas.add(apiResp);
				}
			}
		} catch (Exception e) {
			logger.error("Error at: {} - at function: {} - not able to fetch data", this.getClass().getSimpleName(), "fetchSnowFlData");
			throw new RuntimeException("Error at: " + "fetchSnowFlData", e);
		}
		return allDatas;
	}
}
