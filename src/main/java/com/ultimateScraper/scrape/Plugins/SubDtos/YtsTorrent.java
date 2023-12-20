package com.ultimateScraper.scrape.Plugins.SubDtos;

import java.io.Serializable;

public class YtsTorrent implements Serializable{
	private String url;
	private String hash;
	private String quality;
	private String type;

	private int seeds;
	private int peers;
	private String size;
	private long date_uploaded_unix;

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

	public int getSeeds() {
		return seeds;
	}

	public void setSeeds(int seeds) {
		this.seeds = seeds;
	}

	public int getPeers() {
		return peers;
	}

	public void setPeers(int peers) {
		this.peers = peers;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public long getDate_uploaded_unix() {
		return date_uploaded_unix;
	}

	public void setDate_uploaded_unix(long date_uploaded_unix) {
		this.date_uploaded_unix = date_uploaded_unix;
	}

}
