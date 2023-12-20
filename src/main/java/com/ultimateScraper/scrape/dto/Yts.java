package com.ultimateScraper.scrape.dto;

import java.io.Serializable;

import com.ultimateScraper.scrape.Plugins.SubDtos.YtsMovieDatas;

public class Yts implements Serializable{
	private String status;
	private YtsMovieDatas data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public YtsMovieDatas getData() {
		return data;
	}

	public void setData(YtsMovieDatas data) {
		this.data = data;
	}

}
