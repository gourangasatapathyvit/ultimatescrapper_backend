package com.ultimateScraper.scrape.dto;

public class PirateBay {
	private String id;
	private String name;
	private String info_hash;
	private String leechers;
	private String seeders;
	private String num_files;
	private String size;
	private String username;
	private String added;
	private String status;
	private String category;
	private String imdb;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo_hash() {
		return info_hash;
	}

	public void setInfo_hash(String info_hash) {
		this.info_hash = info_hash;
	}

	public String getLeechers() {
		return leechers;
	}

	public void setLeechers(String leechers) {
		this.leechers = leechers;
	}

	public String getSeeders() {
		return seeders;
	}

	public void setSeeders(String seeders) {
		this.seeders = seeders;
	}

	public String getNum_files() {
		return num_files;
	}

	public void setNum_files(String num_files) {
		this.num_files = num_files;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAdded() {
		return added;
	}

	public void setAdded(String added) {
		this.added = added;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImdb() {
		return imdb;
	}

	public void setImdb(String imdb) {
		this.imdb = imdb;
	}

}
