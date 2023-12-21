package com.ultimateScraper.scrape.dto;

import java.io.Serializable;

public class GenericApiResp implements Serializable {
	private String name;
	private String magnetLink;
	private String size;
	private Integer seed;
	private Integer leech;
	private String uploader;
	private String downLoadLink;
	private String date;
	private String image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMagnetLink() {
		return magnetLink;
	}

	public void setMagnetLink(String magnetLink) {
		this.magnetLink = magnetLink;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDownLoadLink() {
		return downLoadLink;
	}

	public void setDownLoadLink(String downLoadLink) {
		this.downLoadLink = downLoadLink;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getSeed() {
		return seed;
	}

	public void setSeed(Integer seed) {
		this.seed = seed;
	}

	public Integer getLeech() {
		return leech;
	}

	public void setLeech(Integer leech) {
		this.leech = leech;
	}

}
