package com.ultimateScraper.scrape.Plugins.SubDtos;

import java.io.Serializable;

public class YtsTorrent implements Serializable{
	private String url;
	private String hash;
	private String quality;
	private String type;

	private Integer seeds;
	private Integer peers;
	private String size;
	private Long date_uploaded_unix;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSeeds() {
		return seeds;
	}

	public void setSeeds(Integer seeds) {
		this.seeds = seeds;
	}

	public Integer getPeers() {
		return peers;
	}

	public void setPeers(Integer peers) {
		this.peers = peers;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Long getDate_uploaded_unix() {
		return date_uploaded_unix;
	}

	public void setDate_uploaded_unix(Long date_uploaded_unix) {
		this.date_uploaded_unix = date_uploaded_unix;
	}

}
