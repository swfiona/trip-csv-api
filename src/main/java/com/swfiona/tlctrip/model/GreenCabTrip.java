package com.swfiona.tlctrip.model;

public class GreenCabTrip extends TaxiTrip {
	
	private Double ehailFee;
	
	private Integer tripType;
	
	private Double conSurcharge;

	public Double getEhailFee() {
		return ehailFee;
	}

	public void setEhailFee(Double ehailFee) {
		this.ehailFee = ehailFee;
	}

	public Integer getTripType() {
		return tripType;
	}

	public void setTripType(Integer tripType) {
		this.tripType = tripType;
	}

	public Double getConSurcharge() {
		return conSurcharge;
	}

	public void setConSurcharge(Double conSurcharge) {
		this.conSurcharge = conSurcharge;
	}
	
}
