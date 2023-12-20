package com.ultimateScraper.scrape.dto;

import java.io.Serializable;
import java.util.List;

public class ApiResponse implements Serializable{
	 private String status;
	    private int length;
	    private boolean cacheHit;
	    private double elapsed;
	    private String errMessage;
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
		public String getErrMessage() {
			return errMessage;
		}
		public void setErrMessage(String errMessage) {
			this.errMessage = errMessage;
		}
}
