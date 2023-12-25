package com.ultimateScraper.scrape.Plugins.SubDtos;

import java.io.Serializable;
import java.util.List;

public class YtsMovieDatas{
	private int movie_count;
	private int page_number;
	private List<YtsMovie> movies;

	public int getMovie_count() {
		return movie_count;
	}

	public void setMovie_count(int movie_count) {
		this.movie_count = movie_count;
	}

	public int getPage_number() {
		return page_number;
	}

	public void setPage_number(int page_number) {
		this.page_number = page_number;
	}

	public List<YtsMovie> getMovies() {
		return movies;
	}

	public void setMovies(List<YtsMovie> movies) {
		this.movies = movies;
	}

}
