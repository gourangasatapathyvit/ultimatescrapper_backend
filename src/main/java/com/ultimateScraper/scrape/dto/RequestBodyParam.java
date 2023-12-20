package com.ultimateScraper.scrape.dto;

import java.io.Serializable;
import java.util.List;

public class RequestBodyParam implements Serializable{
	private String inputQuery;
	private List<String> source;
	private String catagory;


	public String getInputQuery() {
		return inputQuery;
	}

	public void setInputQuery(String inputQuery) {
		this.inputQuery = inputQuery;
	}

	public List<String> getSource() {
		return source;
	}

	public void setSource(List<String> source) {
		this.source = source;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

}
