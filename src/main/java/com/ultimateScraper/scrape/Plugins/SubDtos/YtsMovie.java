package com.ultimateScraper.scrape.Plugins.SubDtos;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

public class YtsMovie extends JdkSerializationRedisSerializer implements Serializable{
	private int id;
	private String url;
	private String imdb_code;
	private String title_english;
	private int year;
	private double rating;
	private List<String> genres;
	private String summary;
	private String description_full;
	private String language;
	private String background_image;
	private String background_image_original;
	private String small_cover_image;
	private String medium_cover_image;
	private String large_cover_image;
	private List<YtsTorrent> torrents;
	private String date_uploaded_unix;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImdb_code() {
		return imdb_code;
	}

	public void setImdb_code(String imdb_code) {
		this.imdb_code = imdb_code;
	}

	public String getTitle_english() {
		return title_english;
	}

	public void setTitle_english(String title_english) {
		this.title_english = title_english;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription_full() {
		return description_full;
	}

	public void setDescription_full(String description_full) {
		this.description_full = description_full;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getBackground_image() {
		return background_image;
	}

	public void setBackground_image(String background_image) {
		this.background_image = background_image;
	}

	public String getBackground_image_original() {
		return background_image_original;
	}

	public void setBackground_image_original(String background_image_original) {
		this.background_image_original = background_image_original;
	}

	public String getSmall_cover_image() {
		return small_cover_image;
	}

	public void setSmall_cover_image(String small_cover_image) {
		this.small_cover_image = small_cover_image;
	}

	public String getMedium_cover_image() {
		return medium_cover_image;
	}

	public void setMedium_cover_image(String medium_cover_image) {
		this.medium_cover_image = medium_cover_image;
	}

	public String getLarge_cover_image() {
		return large_cover_image;
	}

	public void setLarge_cover_image(String large_cover_image) {
		this.large_cover_image = large_cover_image;
	}

	public List<YtsTorrent> getTorrents() {
		return torrents;
	}

	public void setTorrents(List<YtsTorrent> torrents) {
		this.torrents = torrents;
	}

	public String getDate_uploaded_unix() {
		return date_uploaded_unix;
	}

	public void setDate_uploaded_unix(String date_uploaded_unix) {
		this.date_uploaded_unix = date_uploaded_unix;
	}

}
