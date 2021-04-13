package com.swfiona.tlctrip.model;

public class Trip {
	
	private Integer pickUpLoc;
	
	private Integer dropOffLoc;
	
	private String pickUpTime;
	
	private String dropOffTime;
	
	public Integer getPickUpLoc() {
		return pickUpLoc;
	}

	public void setPickUpLoc(Integer pickUpLoc) {
		this.pickUpLoc = pickUpLoc;
	}

	public Integer getDropOffLoc() {
		return dropOffLoc;
	}

	public void setDropOffLoc(Integer dropOffLoc) {
		this.dropOffLoc = dropOffLoc;
	}

	public String getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(String pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public String getDropOffTime() {
		return dropOffTime;
	}

	public void setDropOffTime(String dropOffTime) {
		this.dropOffTime = dropOffTime;
	}
}
