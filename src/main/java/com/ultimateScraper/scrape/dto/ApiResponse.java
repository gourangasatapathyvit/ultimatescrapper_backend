package com.ultimateScraper.scrape.dto;

import java.util.List;

public class ApiResponse {
	 private String status;
	    private int length;
	    private boolean cacheHit;
	    private double elapsed;
	    private List<DataItem> data;
	    
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
		public boolean isCacheHit() {
			return cacheHit;
		}
		public void setCacheHit(boolean cacheHit) {
			this.cacheHit = cacheHit;
		}
		public double getElapsed() {
			return elapsed;
		}
		public void setElapsed(double elapsed) {
			this.elapsed = elapsed;
		}
		public List<DataItem> getData() {
			return data;
		}
		public void setData(List<DataItem> data) {
			this.data = data;
		}
	

}
