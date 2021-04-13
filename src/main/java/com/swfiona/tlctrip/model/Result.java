package com.swfiona.tlctrip.model;

import java.util.List;

public class Result {
	
	private int total;
	
	private List<Trip> tripResults;
	
	public List<Trip> getTripResults() {
		return tripResults;
	}

	public void setTripResults(List<Trip> tripResults) {
		this.tripResults = tripResults;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
