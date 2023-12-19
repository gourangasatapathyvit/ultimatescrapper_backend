package com.ultimateScraper.scrape.dto;

import java.util.Arrays;
import java.util.List;

public class ReqDto {

	private List<String> exclude_categories;
	private List<String> include_categories;
	private List<String> exclude_sites;
	private List<String> include_sites;
	private String search_term;
	
	
	public ReqDto() {
		super();
	}


	public List<String> getExclude_categories() {
		return exclude_categories;
	}


	public void setExclude_categories(List<String> exclude_categories) {
		this.exclude_categories = exclude_categories;
	}


	public List<String> getInclude_categories() {
		return include_categories;
	}


	public void setInclude_categories(List<String> include_categories) {
		this.include_categories = include_categories;
	}


	public List<String> getExclude_sites() {
		return exclude_sites;
	}


	public void setExclude_sites(List<String> exclude_sites) {
		this.exclude_sites = exclude_sites;
	}


	public List<String> getInclude_sites() {
		return include_sites;
	}


	public void setInclude_sites(List<String> include_sites) {
		this.include_sites = include_sites;
	}


	public String getSearch_term() {
		return search_term;
	}


	public void setSearch_term(String search_term) {
		this.search_term = search_term;
	}


	@Override
	public String toString() {
		return "ResDto [exclude_categories=" + exclude_categories + ", include_categories=" + include_categories
				+ ", exclude_sites=" + exclude_sites + ", include_sites=" + include_sites + ", searchTerm=" + search_term
				+ "]";
	}
	
	
	
}
